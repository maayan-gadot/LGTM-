package com.project;

import java.util.List;

public class TraceConfig {

    private List<Trace> traces;

    public List<Trace> getTraces() {
        return traces;
    }

    public void setTraces(List<Trace> traces) {
        this.traces = traces;
    }

    public static class Trace {
        private String traceType;
        private StartEndCondition start;
        private StartEndCondition end;
        private List<Span> spans;

        public String getTraceType() {
            return traceType;
        }

        public void setTraceType(String traceType) {
            this.traceType = traceType;
        }

        public StartEndCondition getStart() {
            return start;
        }

        public void setStart(StartEndCondition start) {
            this.start = start;
        }

        public StartEndCondition getEnd() {
            return end;
        }

        public void setEnd(StartEndCondition end) {
            this.end = end;
        }

        public List<Span> getSpans() {
            return spans;
        }

        public void setSpans(List<Span> spans) {
            this.spans = spans;
        }
    }

    public static class StartEndCondition {
        private String source;
        private String destination;
        private String messageType;
        private Attributes attributes;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public Attributes getAttributes() {
            return attributes;
        }

        public void setAttributes(Attributes attributes) {
            this.attributes = attributes;
        }
    }

    public static class Span {
        private String name;
        private StartEndCondition start;
        private StartEndCondition end;
        private Attributes attributes;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public StartEndCondition getStart() {
            return start;
        }

        public void setStart(StartEndCondition start) {
            this.start = start;
        }

        public StartEndCondition getEnd() {
            return end;
        }

        public void setEnd(StartEndCondition end) {
            this.end = end;
        }

        public Attributes getAttributes() {
            return attributes;
        }

        public void setAttributes(Attributes attributes) {
            this.attributes = attributes;
        }
    }

    public static class Attributes {
        private String field;
        private String from;
        private String to;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }
    }
}

///////////////////////////////////

// import java.util.List;

// public class TraceConfig {
//     private List<Trace> traces;

//     public List<Trace> getTraces() {
//         return traces;
//     }

//     public void setTraces(List<Trace> traces) {
//         this.traces = traces;
//     }

//     public static class Trace {
//         private String traceType;
//         private StartEndCondition start;
//         private StartEndCondition end;
//         private List<Span> spans;

//         public String getTraceType() {
//             return traceType;
//         }

//         public void setTraceType(String traceType) {
//             this.traceType = traceType;
//         }

//         public StartEndCondition getStart() {
//             return start;
//         }

//         public void setStart(StartEndCondition start) {
//             this.start = start;
//         }

//         public StartEndCondition getEnd() {
//             return end;
//         }

//         public void setEnd(StartEndCondition end) {
//             this.end = end;
//         }

//         public List<Span> getSpans() {
//             return spans;
//         }

//         public void setSpans(List<Span> spans) {
//             this.spans = spans;
//         }
//     }

//     public static class Span {
//         private String name;
//         private StartEndCondition start;
//         private StartEndCondition end;
//         private SpanAttributes attributes;

//         public String getName() {
//             return name;
//         }

//         public void setName(String name) {
//             this.name = name;
//         }

//         public StartEndCondition getStart() {
//             return start;
//         }

//         public void setStart(StartEndCondition start) {
//             this.start = start;
//         }

//         public StartEndCondition getEnd() {
//             return end;
//         }

//         public void setEnd(StartEndCondition end) {
//             this.end = end;
//         }

//         public SpanAttributes getAttributes() {
//             return attributes;
//         }

//         public void setAttributes(SpanAttributes attributes) {
//             this.attributes = attributes;
//         }
//     }

//     public static class StartEndCondition {
//         private String source;
//         private String destination;
//         private String messageType;

//         public String getSource() {
//             return source;
//         }

//         public void setSource(String source) {
//             this.source = source;
//         }

//         public String getDestination() {
//             return destination;
//         }

//         public void setDestination(String destination) {
//             this.destination = destination;
//         }

//         public String getMessageType() {
//             return messageType;
//         }

//         public void setMessageType(String messageType) {
//             this.messageType = messageType;
//         }
//     }

//     public static class SpanAttributes {
//         private String field;
//         private String from;
//         private String to;

//         public String getField() {
//             return field;
//         }

//         public void setField(String field) {
//             this.field = field;
//         }

//         public String getFrom() {
//             return from;
//         }

//         public void setFrom(String from) {
//             this.from = from;
//         }

//         public String getTo() {
//             return to;
//         }

//         public void setTo(String to) {
//             this.to = to;
//         }
//     }
// }



//------------------------------------------------

// import java.util.List;
// import java.util.Map;

// public class TraceConfig {
//     private List<Trace> traces;

//     public List<Trace> getTraces() {
//         return traces;
//     }

//     public void setTraces(List<Trace> traces) {
//         this.traces = traces;
//     }

//     public static class Trace {
//         private String traceType;
//         private StartEndCondition start;
//         private StartEndCondition end;
//         private String field;  
//         private List<Span> spans;

//         public String getTraceType() { return traceType; }
//         public void setTraceType(String traceType) { this.traceType = traceType; }
//         public StartEndCondition getStart() { return start; }
//         public void setStart(StartEndCondition start) { this.start = start; }
//         public StartEndCondition getEnd() { return end; }
//         public void setEnd(StartEndCondition end) { this.end = end; }
//         public String getField() { return field; }  
//         public void setField(String field) { this.field = field; }  
//         public List<Span> getSpans() { return spans; }
//         public void setSpans(List<Span> spans) { this.spans = spans; }
//     }

    
//     public static class Span {
//         private String name;
//         private StartEndCondition start;
//         private StartEndCondition end;
//         private String field;
//         private String from;
//         private String to;
//         private Map<String, String> attributes;

//         public String getName() { return name; }
//         public void setName(String name) { this.name = name; }
        
//         public Map<String, String> getAttributes() { return attributes; }    
//         public void setAttributes(Map<String, String> attributes) { this.attributes = attributes; }
        
//         public StartEndCondition getStart() { return start; }
//         public void setStart(StartEndCondition start) { this.start = start; }
        
//         public StartEndCondition getEnd() { return end; }
//         public void setEnd(StartEndCondition end) { this.end = end; }
        
//         public String getField() { return field; }  
//         public void setField(String field) { this.field = field; }  
        
//         public String getFrom() { return from; }
//         public void setFrom(String from) { this.from = from; }
    
//         public String getTo() { return to; }
//         public void setTo(String to) { this.to = to; }
        
//     }

//     public static class StartEndCondition {
//         private String source;
//         private String destination;
//         private String messageType;

//         public String getSource() { return source; }
//         public void setSource(String source) { this.source = source; }
        
//         public String getDestination() { return destination; }
//         public void setDestination(String destination) { this.destination = destination; }
        
//         public String getMessageType() { return messageType; }
//         public void setMessageType(String messageType) { this.messageType = messageType; }
//     }
// }

// -----------------------------------------------------
// public class TraceConfig {
// /    private List<Trace> traces;

//     public List<Trace> getTraces() {
//         return traces;
//     }

//     public void setTraces(List<Trace> traces) {
//         this.traces = traces;
//     }

//     public static class Trace {
//         private String traceType;
//         private StartEndCondition start;
//         private StartEndCondition end;
//         private List<Span> spans;

//         public String getTraceType() {
//             return traceType;
//         }

//         public void setTraceType(String traceType) {
//             this.traceType = traceType;
//         }

//         public StartEndCondition getStart() {
//             return start;
//         }

//         public void setStart(StartEndCondition start) {
//             this.start = start;
//         }

//         public StartEndCondition getEnd() {
//             return end;
//         }

//         public void setEnd(StartEndCondition end) {
//             this.end = end;
//         }

//         public List<Span> getSpans() {
//             return spans;
//         }

//         public void setSpans(List<Span> spans) {
//             this.spans = spans;
//         }
//     }

//     public static class Span {
//         private String name;
//         private StartEndCondition start;
//         private StartEndCondition end;
//         private Map<String, String> attributes;

//         public String getName() {
//             return name;
//         }

//         public void setName(String name) {
//             this.name = name;
//         }

//         public StartEndCondition getStart() {
//             return start;
//         }

//         public void setStart(StartEndCondition start) {
//             this.start = start;
//         }

//         public StartEndCondition getEnd() {
//             return end;
//         }

//         public void setEnd(StartEndCondition end) {
//             this.end = end;
//         }

//         public Map<String, String> getAttributes() {
//             return attributes;
//         }

//         public void setAttributes(Map<String, String> attributes) {
//             this.attributes = attributes;
//         }
//     }

//     public static class StartEndCondition {
//         private String source;
//         private String destination;
//         private String messageType;

//         public String getSource() {
//             return source;
//         }

//         public void setSource(String source) {
//             this.source = source;
//         }

//         public String getDestination() {
//             return destination;
//         }

//         public void setDestination(String destination) {
//             this.destination = destination;
//         }

//         public String getMessageType() {
//             return messageType;
//         }

//         public void setMessageType(String messageType) {
//             this.messageType = messageType;
//         }
//     }
// }

//--------------------------
//code B
// public class TraceConfig {
//     private List<Trace> traces;

//     public List<Trace> getTraces() {
//         return traces;
//     }

//     public void setTraces(List<Trace> traces) {
//         this.traces = traces;
//     }

//     public static class Trace {
//         private String traceId;  
//         private String traceName; // <-- Add this field

//         private List<Span> spans;

//         public String getTraceId() { 
//             return traceId; 
//         }  
        
//         public void setTraceId(String traceId) { 
//             this.traceId = traceId; 
//         } 

//         public String getTraceName() {
//             return traceName;
//         }
//         public void setTraceName(String traceName) { 
//             this.traceName = traceName;
//         }
//         public List<Span> getSpans() { 
//             return spans; 
//         }
//         public void setSpans(List<Span> spans) { 
//             this.spans = spans; 
//         }
//     }

//     public static class Span {
//         private String name;
//         private String source;
//         private String destination;
//         private Map<String, String> attributes;

//         public String getName() { 
//             return name; 
//         }
        
//         public void setName(String name) { 
//             this.name = name; 
//         }

//         public String getSource() { 
//             return source; 
//         }
        
//         public void setSource(String source) { 
//             this.source = source; 
//         }

//         public String getDestination() { 
//             return destination; 
//         }
        
//         public void setDestination(String destination) { 
//             this.destination = destination; 
//         }

//         public Map<String, String> getAttributes() { 
//             return attributes; 
//         }
        
//         public void setAttributes(Map<String, String> attributes) { 
//             this.attributes = attributes; 
//         }
//     }
// }



//---------------------------------------------------------------
// package com.project;

// import java.util.List;
// import java.util.Map;

// public class TraceConfig {
//     private TraceWrapper traces;

//     public TraceWrapper getTraces() {
//         return traces;
//     }

//     public void setTraces(TraceWrapper traces) {
//         this.traces = traces;
//     }

//     public static class TraceWrapper {
//         private List<Span> spans;

//         public List<Span> getSpans() {
//             return spans;
//         }

//         public void setSpans(List<Span> spans) {
//             this.spans = spans;
//         }
//     }

//     public static class Span {
//         private String name;
//         private String source;
//         private String destination;
//         private Map<String, String> attributes;

//         public String getName() {
//             return name;
//         }

//         public void setName(String name) {
//             this.name = name;
//         }

//         public String getSource() {
//             return source;
//         }

//         public void setSource(String source) {
//             this.source = source;
//         }

//         public String getDestination() {
//             return destination;
//         }

//         public void setDestination(String destination) {
//             this.destination = destination;
//         }

//         public Map<String, String> getAttributes() {
//             return attributes;
//         }

//         public void setAttributes(Map<String, String> attributes) {
//             this.attributes = attributes;
//         }
//     }
// }
