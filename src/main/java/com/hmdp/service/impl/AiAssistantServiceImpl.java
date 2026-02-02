package com.hmdp.service.impl;

import com.hmdp.ai.agent.AgentContext;
import com.hmdp.ai.orchestrator.TripPlannerOrchestrator;
import com.hmdp.dto.AiTripPlanRequest;
import com.hmdp.dto.Result;
import com.hmdp.service.IAiAssistantService;
import org.springframework.stereotype.Service;

@Service
public class AiAssistantServiceImpl implements IAiAssistantService {

    private final TripPlannerOrchestrator orchestrator;

    public AiAssistantServiceImpl(TripPlannerOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @Override
    public Result planTrip(AiTripPlanRequest request) {
        AgentContext context = orchestrator.run(request);
        Object plan = context.get("final_plan");
        if (plan == null) {
            return Result.fail("AI生成失败，请稍后重试");
        }
        return Result.ok(plan);
    }
}
