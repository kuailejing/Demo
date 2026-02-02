package com.hmdp.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ai")
public class AiProperties {

    private Mcp mcp = new Mcp();
    private Llm llm = new Llm();

    @Data
    public static class Mcp {
        private String baseUrl;
        private String apiKey;
        private int timeoutMs = 15000;
        private Tools tools = new Tools();
    }

    @Data
    public static class Tools {
        private String poiKeyword;
        private String weatherCity;
        private String distance;
    }

    @Data
    public static class Llm {
        private String apiBase;
        private String apiKey;
        private String model;
        private int timeoutMs = 20000;
    }
}
