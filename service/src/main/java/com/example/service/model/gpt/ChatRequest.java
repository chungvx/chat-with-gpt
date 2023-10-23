package com.example.service.model.gpt;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Jacksonized
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private Integer n = 1;
    private Double temperature;

    public ChatRequest(String model, String prompt) {
        this.model = model;
        this.messages = List.of(new Message("user", prompt));
    }
}
