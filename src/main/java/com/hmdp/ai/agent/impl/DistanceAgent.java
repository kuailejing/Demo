package com.hmdp.ai.agent.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.hmdp.ai.agent.Agent;
import com.hmdp.ai.agent.AgentContext;
import com.hmdp.ai.config.AiProperties;
import com.hmdp.ai.mcp.McpClient;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DistanceAgent implements Agent {

    private final McpClient mcpClient;
    private final AiProperties properties;

    public DistanceAgent(McpClient mcpClient, AiProperties properties) {
        this.mcpClient = mcpClient;
        this.properties = properties;
    }

    @Override
    public String name() {
        return "Distance-Agent";
    }

    @Override
    public void execute(AgentContext context) {
        String toolName = properties.getMcp().getTools().getDistance();
        if (StrUtil.isBlank(toolName)) {
            return;
        }
        Object poiObj = context.get("poi_result");
        if (!(poiObj instanceof JSONObject)) {
            return;
        }
        JSONObject poiResult = (JSONObject) poiObj;
        JSONArray pois = extractPois(poiResult);
        if (pois == null || pois.size() < 2) {
            return;
        }

        String origin = extractLocation(pois.getJSONObject(0));
        String destination = extractLocation(pois.getJSONObject(1));
        if (origin == null || destination == null) {
            return;
        }

        Map<String, Object> args = new HashMap<>();
        args.put("origins", origin);
        args.put("destination", destination);

        context.put("distance_result", mcpClient.callTool(toolName, args));
    }

    private JSONArray extractPois(JSONObject poiResult) {
        if (poiResult.containsKey("pois")) {
            return poiResult.getJSONArray("pois");
        }
        if (poiResult.containsKey("data")) {
            JSONObject data = poiResult.getJSONObject("data");
            if (data != null && data.containsKey("pois")) {
                return data.getJSONArray("pois");
            }
        }
        return null;
    }

    private String extractLocation(JSONObject poi) {
        if (poi == null) {
            return null;
        }
        if (poi.containsKey("location")) {
            return poi.getStr("location");
        }
        if (poi.containsKey("lng") && poi.containsKey("lat")) {
            return poi.getStr("lng") + "," + poi.getStr("lat");
        }
        return null;
    }
}
