package com.hmdp.ai.agent;

public interface Agent {
    String name();
    void execute(AgentContext context);
}
