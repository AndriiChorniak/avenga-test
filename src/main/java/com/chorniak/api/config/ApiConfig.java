package com.chorniak.api.config;

import com.chorniak.api.utility.PropertyReader;

public class ApiConfig {
    private static final String DEFAULT_URI = "http://localhost:8080";

    public static String getBaseUri() {
        return System.getProperty("api.base.uri", PropertyReader.get("api.base.uri", DEFAULT_URI));
    }

    public static String getBasePath() {
        return System.getProperty("api.base.path", PropertyReader.get("api.base.path", ""));
    }

}
