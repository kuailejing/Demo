package com.hmdp.ai.agent.impl;

import cn.hutool.json.JSONUtil;
import com.hmdp.ai.agent.Agent;
import com.hmdp.ai.agent.AgentContext;
import com.hmdp.ai.llm.DeepSeekClient;
import com.hmdp.dto.AiTripPlanRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PlannerAgent implements Agent {

    private final DeepSeekClient deepSeekClient;

    public PlannerAgent(DeepSeekClient deepSeekClient) {
        this.deepSeekClient = deepSeekClient;
    }

    @Override
    public String name() {
        return "Planner-Agent";
    }

    @Override
    public void execute(AgentContext context) {
        AiTripPlanRequest request = context.getRequest();
        String prompt = buildPrompt(request, context);
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(msg("system", "You are a travel planning assistant. Output concise JSON."));
        messages.add(msg("user", prompt));

        String content = deepSeekClient.chat(messages);
        context.put("final_plan", content);
    }

    private String buildPrompt(AiTripPlanRequest request, AgentContext context) {
        Map<String, Object> data = new HashMap<>();
        data.put("request", request);
        data.put("poi_result", context.get("poi_result"));
        data.put("weather_result", context.get("weather_result"));
        data.put("distance_result", context.get("distance_result"));

        String contextJson = JSONUtil.toJsonStr(data);
        return "Generate a " + request.getTravelDays() + "-day travel plan in Chinese. " +
                "Use the provided context JSON. Output JSON with fields: title, days, tips. " +
                "Context: " + contextJson;
    }

    private Map<String, String> msg(String role, String content) {
        Map<String, String> map = new HashMap<>();
        map.put("role", role);
        map.put("content", content);
        return map;
    }
}
