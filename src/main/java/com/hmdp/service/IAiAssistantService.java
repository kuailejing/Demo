package com.hmdp.service;

import com.hmdp.dto.AiTripPlanRequest;
import com.hmdp.dto.Result;

public interface IAiAssistantService {
    Result planTrip(AiTripPlanRequest request);
}
