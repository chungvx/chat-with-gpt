package com.example.service.model.gpt;

import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {
    private String id;
    private String object;
    private int created;
    private String model;
    private List<ChoiceResponse> choices;
    private UsageResponse usage;
}
