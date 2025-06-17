package com.project;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties file not found!");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading config.properties", e);
        }
    }

    public static String getInfluxDbToken() {
        String token = properties.getProperty("influxdb.token");
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("InfluxDB token is missing from config.properties");
        }
        System.out.println("Loaded InfluxDB Token: " + token);
        return token;
    }

    public static String getInfluxDbOrg() {
        String org = properties.getProperty("influxdb.org");
        if (org == null || org.isEmpty()) {
            throw new RuntimeException("InfluxDB org is missing from config.properties");
        }
        System.out.println("Loaded InfluxDB org: " + org);
        return org;
    }

    public static String getInfluxDbBucket() {
        String bucket = properties.getProperty("influxdb.bucket");
        if (bucket == null || bucket.isEmpty()) {
            throw new RuntimeException("InfluxDB bucket is missing from config.properties");
        }
        System.out.println("Loaded InfluxDB org: " + bucket);
        return bucket;
    }
    

}
