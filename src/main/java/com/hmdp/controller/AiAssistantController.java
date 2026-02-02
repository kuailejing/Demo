package com.hmdp.controller;

import com.hmdp.dto.AiTripPlanRequest;
import com.hmdp.dto.Result;
import com.hmdp.ai.mcp.McpClient;
import com.hmdp.service.IAiAssistantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ai")
public class AiAssistantController {

    @Resource
    private IAiAssistantService aiAssistantService;

    @Resource
    private McpClient mcpClient;

    @PostMapping("/trip/plan")
    public Result planTrip(@RequestBody AiTripPlanRequest request) {
        return aiAssistantService.planTrip(request);
    }

    @GetMapping("/mcp/tools")
    public Result listMcpTools() {
        return Result.ok(mcpClient.listTools());
    }
}
