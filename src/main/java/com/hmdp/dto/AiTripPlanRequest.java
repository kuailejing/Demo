package com.hmdp.dto;

import lombok.Data;

import java.util.List;

@Data
public class AiTripPlanRequest {
    private String city;
    private String startDate;
    private String endDate;
    private Integer travelDays;
    private String transportation;
    private String accommodation;
    private List<String> preferences;
    private String freeTextInput;
}
