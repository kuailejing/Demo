package com.hmdp.ai.agent.impl;

import com.hmdp.ai.agent.Agent;
import com.hmdp.ai.agent.AgentContext;
import com.hmdp.ai.config.AiProperties;
import com.hmdp.ai.mcp.McpClient;
import com.hmdp.dto.AiTripPlanRequest;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WeatherAgent implements Agent {

    private final McpClient mcpClient;
    private final AiProperties properties;

    public WeatherAgent(McpClient mcpClient, AiProperties properties) {
        this.mcpClient = mcpClient;
        this.properties = properties;
    }

    @Override
    public String name() {
        return "Weather-Agent";
    }

    @Override
    public void execute(AgentContext context) {
        AiTripPlanRequest request = context.getRequest();
        String toolName = properties.getMcp().getTools().getWeatherCity();
        if (StrUtil.isBlank(toolName)) {
            return;
        }
        Map<String, Object> args = new HashMap<>();
        args.put("city", request.getCity());

        context.put("weather_result", mcpClient.callTool(toolName, args));
    }
}
