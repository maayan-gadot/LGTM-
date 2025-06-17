// package com.project;

// import org.yaml.snakeyaml.Yaml;
// import java.io.InputStream;
// import java.util.List;
// import java.util.Map;




// public class ConfigLoader {


//     public static class SpanConfig {
//         public String name;
//         public String source;
//         public String destination;
//         public Map<String, String> attributes;

//         public SpanConfig() {}
//     }

//     public static class TraceConfig {
//         public List<SpanConfig> spans;

//         public TraceConfig() {}
//     }

//     public static TraceConfig loadYaml(String filePath) {
//         Yaml yaml = new Yaml();

//         try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(filePath)) {
//             if (inputStream == null) {
//                 throw new RuntimeException("YAML configuration file not found: " + filePath);
//             }
//             return yaml.loadAs(inputStream, TraceConfig.class); 
//         } catch (Exception e) {
//             throw new RuntimeException("Failed to load YAML file: " + filePath, e);
//         }
//     }
// }




//------------------------
package com.project;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;

public class ConfigLoader {

    public static TraceConfig loadYaml(String filePath) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new RuntimeException("YAML file not found: " + filePath);
            }
            return yaml.loadAs(inputStream, TraceConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Error loading YAML file: " + filePath, e);
        }
    }
}
