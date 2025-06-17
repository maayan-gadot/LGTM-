package com.project;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import java.time.Duration;

public class OpenTelemetryConfig {

        private static SdkTracerProvider tracerProvider;

        public static OpenTelemetry initializeOpenTelemetry(String serviceName) {
                // Configure OTLP Exporter
                OtlpGrpcSpanExporter exporter = OtlpGrpcSpanExporter.builder()
                                .setEndpoint("http://localhost:4317")
                                .build();
                
                // BatchSpanProcessor batchSpanProcessor = BatchSpanProcessor.builder(exporter)
                //                 .setScheduleDelay(Duration.ofMillis(500)) // Export spans every 500ms
                //                 .build();

                // Create Resource
                Resource resource = Resource.builder()
                                .put("service.name", serviceName)
                                .build();

                
                // Configure Tracer Provider
                // tracerProvider = SdkTracerProvider.builder()
                //                 .setResource(resource)
                //                 .setSampler(Sampler.alwaysOn())
                //                 .addSpanProcessor(batchSpanProcessor)
                //                 .build();
                tracerProvider = SdkTracerProvider.builder()
                                .setResource(resource)
                                .setSampler(Sampler.alwaysOn())
                                .addSpanProcessor(SimpleSpanProcessor.create(exporter))
                                .build();

                

                // Build and register OpenTelemetry
                OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
                                .setTracerProvider(tracerProvider)
                                // .buildAndRegisterGlobal();
                                .build();
                System.out.println("OpenTelemetry initialized");

                return openTelemetry;
        }

        // Provide access to the tracer provider for shutdown
        public static SdkTracerProvider getTracerProvider() {
                return tracerProvider;
        }

        public static void shutdown() {
                if (tracerProvider != null) {
                        tracerProvider.shutdown();
                }
        }
}

// package com.project;

// import io.opentelemetry.sdk.OpenTelemetrySdk;
// import io.opentelemetry.sdk.resources.Resource;
// import io.opentelemetry.sdk.trace.SdkTracerProvider;
// import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
// import io.opentelemetry.sdk.trace.samplers.Sampler;
// import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
// import io.opentelemetry.api.OpenTelemetry;
// import io.opentelemetry.api.trace.Tracer;

// public class OpenTelemetryConfig {
// public static Tracer initializeOpenTelemetry() {
// OtlpGrpcSpanExporter exporter = OtlpGrpcSpanExporter.builder()
// .setEndpoint("http://localhost:4317")
// .build();

// // Create a Resource with the service.name
// Resource resource = Resource.builder()
// .put("service.name", "pmd_service") // Set the service name here
// .build();

// SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
// .setResource(resource)
// .setSampler(Sampler.alwaysOn()) // Record 100% of spans
// .addSpanProcessor(BatchSpanProcessor.builder(exporter).build()) // Export
// spans in
// // batches
// .build();

// // Create the OpenTelemetry SDK and get a Tracer
// Tracer tracer = OpenTelemetrySdk.builder()
// .setTracerProvider(tracerProvider)
// .build()
// .getTracer("pmd_tracer");

// return tracer;
// }
// }
