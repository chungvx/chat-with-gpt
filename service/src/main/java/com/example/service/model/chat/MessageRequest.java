package com.example.service.model.chat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize
public class MessageRequest {
    private String from;
    private String text;
}
