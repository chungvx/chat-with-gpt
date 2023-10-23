package com.example.service.model.chat;

import lombok.Data;

@Data
public class MessageRequest {
    private String from;
    private String text;
}
