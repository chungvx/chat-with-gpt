package com.example.service.model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {
    private String from;
    private String text;
    private String time;
}
