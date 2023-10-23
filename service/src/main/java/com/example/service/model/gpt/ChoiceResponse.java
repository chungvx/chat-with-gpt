package com.example.service.model.gpt;

import lombok.Data;

@Data
public class ChoiceResponse {
    private int index;
    private String finishReason;
    private Message message;
}
