package com.project;


import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import com.influxdb.client.*;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

public class Main {
    private static final String INFLUXDB_URL = "http://localhost:8086";
    private static final String INFLUXDB_TOKEN = Config.getInfluxDbToken();
    private static final String INFLUXDB_ORG = Config.getInfluxDbOrg();
    private static final String INFLUXDB_BUCKET = Config.getInfluxDbBucket();
    private static final String SERVICE_NAME = "pmd-service-A";
    private static final boolean USE_SCHEDULING = false;

    private static final Set<String> activeFieldTraceKeys = new HashSet<>();

    public static void main(String[] args) {
        TraceConfig traceConfig = ConfigLoader.loadYaml("traces-config.yaml");
        OpenTelemetry openTelemetry = OpenTelemetryConfig.initializeOpenTelemetry(SERVICE_NAME);
        Tracer tracer = openTelemetry.getTracer("pmd-tracer");

        if (USE_SCHEDULING) {
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(() -> fetchAndProcess(traceConfig, tracer), 0, 2, TimeUnit.MINUTES);
        } else {
            fetchAndProcess(traceConfig, tracer);
        }
    }

    private static void fetchAndProcess(TraceConfig traceConfig, Tracer tracer) {
        InfluxDBClient client = InfluxDBClientFactory.create(INFLUXDB_URL, INFLUXDB_TOKEN.toCharArray(), INFLUXDB_ORG);
        QueryApi queryApi = client.getQueryApi();

        String query = String.format(
                "from(bucket: \"%s\") |> range(start: time(v: 0)) |> filter(fn: (r) => exists r._value)",
                INFLUXDB_BUCKET);

        List<FluxTable> tables = queryApi.query(query);
        List<FluxRecord> rawRecords = tables.stream()
                .flatMap(table -> table.getRecords().stream())
                .sorted(Comparator.comparing(FluxRecord::getTime))
                .collect(Collectors.toList());

        Map<String, Map<String, String>> groupedFields = new LinkedHashMap<>();
        Map<String, Instant> timestamps = new HashMap<>();

        for (FluxRecord record : rawRecords) {
            String source = record.getValueByKey("Source").toString();
            String destination = record.getValueByKey("Destination").toString();
            String messageType = record.getMeasurement();
            String key = record.getTime().toString() + "|" + source + "|" + destination + "|" + messageType;

            groupedFields.putIfAbsent(key, new HashMap<>());
            Map<String, String> fieldMap = groupedFields.get(key);
            String field = record.getValueByKey("_field").toString();
            String value = record.getValueByKey("_value").toString();
            fieldMap.put(field, value);

            fieldMap.put("Source", source);
            fieldMap.put("Destination", destination);
            fieldMap.put("MessageType", messageType);

            for (Map.Entry<String, Object> entry : record.getValues().entrySet()) {
                if (entry.getValue() != null && !entry.getKey().startsWith("_")) {
                    fieldMap.put(entry.getKey(), entry.getValue().toString());
                }
            }

            timestamps.putIfAbsent(key, record.getTime());
        }

        processRecords(traceConfig, tracer, groupedFields);
        client.close();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("OpenTelemetry Tracing Completed. Server: " + SERVICE_NAME);
    }

    private static void processRecords(TraceConfig traceConfig, Tracer tracer,
                                       Map<String, Map<String, String>> groupedFields) {

        Map<String, Span> activeTraces = new HashMap<>();
        Map<String, Span> activeSpans = new HashMap<>();
        Map<String, String> spanStates = new HashMap<>();

        for (Map.Entry<String, Map<String, String>> entry : groupedFields.entrySet()) {
            Map<String, String> fields = entry.getValue();
            String source = fields.get("Source");
            String destination = fields.get("Destination");
            String messageType = fields.get("MessageType");

            for (TraceConfig.Trace trace : traceConfig.getTraces()) {
                String traceKey = trace.getTraceType();
                String traceName = fields.getOrDefault("TraceID", trace.getTraceType());
                String traceFieldKey = traceKey + ":field";

                if (!activeTraces.containsKey(traceKey)) {
                    if (trace.getStart().getAttributes() != null) {
                        String field = trace.getStart().getAttributes().getField();
                        String from = trace.getStart().getAttributes().getFrom();
                        if (fields.containsKey(field) && fields.get(field).equals(from)) {
                            Span traceSpan = tracer.spanBuilder(traceName + " - " + traceKey)
                                    .setAttribute("service.name", SERVICE_NAME)
                                    .startSpan();
                            fields.forEach(traceSpan::setAttribute);
                            activeTraces.put(traceKey, traceSpan);
                            System.out.println("Started Field Change Trace: " + traceName);
                        }
                    } else if (matchesStart(trace.getStart(), source, destination, messageType)) {
                        Span traceSpan = tracer.spanBuilder(traceName + " - " + traceKey)
                                .setAttribute("service.name", SERVICE_NAME)
                                .startSpan();
                        fields.forEach(traceSpan::setAttribute);
                        activeTraces.put(traceKey, traceSpan);
                        System.out.println("Started Regular Trace: " + traceName);
                    }
                }

                if (activeTraces.containsKey(traceKey)) {
                    Span traceSpan = activeTraces.get(traceKey);

                    for (TraceConfig.Span spanConfig : trace.getSpans()) {
                        if (spanConfig.getAttributes() != null) {
                            String field = spanConfig.getAttributes().getField();
                            String from = spanConfig.getAttributes().getFrom();
                            String to = spanConfig.getAttributes().getTo();
                            String fieldValue = fields.get(field);
                            String spanKey = traceKey + ":" + spanConfig.getName();
                            String stateKey = spanKey + ":" + field;

                            if (fieldValue != null) {
                                if (fieldValue.equals(from) && !spanStates.containsKey(stateKey)) {
                                    Span span = tracer.spanBuilder(spanConfig.getName() + " from " + from)
                                            .setParent(Context.current().with(traceSpan))
                                            .startSpan();
                                    fields.forEach(span::setAttribute);
                                    activeSpans.put(spanKey, span);
                                    spanStates.put(stateKey, from);
                                    System.out.println("Started Field Span: " + spanConfig.getName());
                                } else if (fieldValue.equals(to) && spanStates.containsKey(stateKey)) {
                                    Span span = activeSpans.remove(spanKey);
                                    if (span != null) {
                                        span.end();
                                        System.out.println("Ended Field Span: " + spanConfig.getName());
                                    }
                                    spanStates.remove(stateKey);
                                }
                            }
                        } else if (spanConfig.getStart() != null && matchesStart(spanConfig.getStart(), source, destination, messageType)) {
                            String spanKey = traceKey + ":" + spanConfig.getName();
                            if (!activeSpans.containsKey(spanKey)) {
                                Span span = tracer.spanBuilder(spanConfig.getName())
                                        .setParent(Context.current().with(traceSpan))
                                        .startSpan();
                                fields.forEach(span::setAttribute);
                                activeSpans.put(spanKey, span);
                                System.out.println("Started Span: " + spanConfig.getName());
                            }
                        } else if (spanConfig.getEnd() != null && matchesEnd(spanConfig.getEnd(), source, destination, messageType)) {
                            String spanKey = traceKey + ":" + spanConfig.getName();
                            Span span = activeSpans.remove(spanKey);
                            if (span != null) {
                                span.end();
                                System.out.println("Ended Span: " + spanConfig.getName());
                            }
                        }
                    }
                }

                if (activeTraces.containsKey(traceKey)) {
                    if (trace.getEnd().getAttributes() != null) {
                        String field = trace.getEnd().getAttributes().getField();
                        String from = trace.getEnd().getAttributes().getFrom();
                        String to = trace.getEnd().getAttributes().getTo();
                        if (fields.containsKey(field) && fields.get(field).equals(to)) {
                            Span traceSpan = activeTraces.remove(traceKey);
                            traceSpan.end();
                            System.out.println("Ended Field Change Trace: " + traceName);
                        }
                    } else if (matchesEnd(trace.getEnd(), source, destination, messageType)) {
                        Span traceSpan = activeTraces.remove(traceKey);
                        traceSpan.end();
                        System.out.println("Ended Regular Trace: " + traceName);
                    }
                }
            }
        }
    }

    private static boolean matchesStart(TraceConfig.StartEndCondition start, String source, String destination,
                                        String messageType) {
        return start.getSource().equals(source) &&
                start.getDestination().equals(destination) &&
                start.getMessageType().equals(messageType);
    }

    private static boolean matchesEnd(TraceConfig.StartEndCondition end, String source, String destination,
                                      String messageType) {
        return end.getSource().equals(source) &&
                end.getDestination().equals(destination) &&
                end.getMessageType().equals(messageType);
    }
}
