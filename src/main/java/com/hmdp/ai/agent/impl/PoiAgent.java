package com.hmdp.ai.agent.impl;

import com.hmdp.ai.agent.Agent;
import com.hmdp.ai.agent.AgentContext;
import com.hmdp.ai.config.AiProperties;
import com.hmdp.ai.mcp.McpClient;
import com.hmdp.dto.AiTripPlanRequest;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PoiAgent implements Agent {

    private final McpClient mcpClient;
    private final AiProperties properties;

    public PoiAgent(McpClient mcpClient, AiProperties properties) {
        this.mcpClient = mcpClient;
        this.properties = properties;
    }

    @Override
    public String name() {
        return "POI-Agent";
    }

    @Override
    public void execute(AgentContext context) {
        AiTripPlanRequest request = context.getRequest();
        String toolName = properties.getMcp().getTools().getPoiKeyword();
        if (StrUtil.isBlank(toolName)) {
            return;
        }
        Map<String, Object> args = new HashMap<>();

        String keywords = buildKeywords(request.getPreferences());
        args.put("keywords", keywords);
        args.put("city", request.getCity());
        args.put("citylimit", true);

        context.put("poi_result", mcpClient.callTool(toolName, args));
    }

    private String buildKeywords(List<String> preferences) {
        if (preferences == null || preferences.isEmpty()) {
            return "景点";
        }
        return String.join(" ", preferences);
    }
}
