package com.example.service.model.gpt;

import lombok.Data;

@Data
public class UsageResponse {
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;
}
