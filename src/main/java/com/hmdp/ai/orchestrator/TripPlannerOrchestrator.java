package com.hmdp.ai.orchestrator;

import com.hmdp.ai.agent.Agent;
import com.hmdp.ai.agent.AgentContext;
import com.hmdp.ai.agent.impl.DistanceAgent;
import com.hmdp.ai.agent.impl.PlannerAgent;
import com.hmdp.ai.agent.impl.PoiAgent;
import com.hmdp.ai.agent.impl.WeatherAgent;
import com.hmdp.dto.AiTripPlanRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TripPlannerOrchestrator {

    private final List<Agent> agents;

    public TripPlannerOrchestrator(PoiAgent poiAgent,
                                   WeatherAgent weatherAgent,
                                   DistanceAgent distanceAgent,
                                   PlannerAgent plannerAgent) {
        this.agents = Arrays.asList(poiAgent, weatherAgent, distanceAgent, plannerAgent);
    }

    public AgentContext run(AiTripPlanRequest request) {
        AgentContext context = new AgentContext(request);
        for (Agent agent : agents) {
            agent.execute(context);
        }
        return context;
    }
}
