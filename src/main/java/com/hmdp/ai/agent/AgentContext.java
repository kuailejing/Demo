package com.hmdp.ai.agent;

import com.hmdp.dto.AiTripPlanRequest;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AgentContext {
    private AiTripPlanRequest request;
    private Map<String, Object> data = new HashMap<>();

    public AgentContext(AiTripPlanRequest request) {
        this.request = request;
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }
}
