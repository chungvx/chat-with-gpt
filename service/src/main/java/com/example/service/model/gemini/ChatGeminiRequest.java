package com.example.service.model.gemini;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Jacksonized
public class ChatGeminiRequest {
    private List<Part> contents;

    public ChatGeminiRequest(String prompt) {
        MessageGemini promptMessage = new MessageGemini(prompt);
        Part promptPart = new Part(List.of(promptMessage));
        this.contents = List.of(promptPart);
    }
}
