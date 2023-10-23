package com.example.service.controller;

import com.example.service.config.OpenAIConfig;
import com.example.service.model.chat.MessageRequest;
import com.example.service.model.chat.MessageResponse;
import com.example.service.model.gpt.ChatResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ChatController {
    private OpenAIConfig openAIConfig;

    @GetMapping("/chat-gpt")
    public String chatGpt(@RequestParam String prompt) {
        // call the API
        ChatResponse response = openAIConfig.sendToGpt(prompt);
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageResponse chat(MessageRequest request) {
        String time = Instant.now().toString();
        return new MessageResponse(request.getFrom(), request.getText(), time);
    }

}
