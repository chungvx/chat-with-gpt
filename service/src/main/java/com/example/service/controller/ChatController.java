package com.example.service.controller;

import com.example.service.config.OpenAIConfig;
import com.example.service.model.chat.MessageRequest;
import com.example.service.model.chat.MessageResponse;
import com.example.service.model.gpt.ChatResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ChatController {
    private OpenAIConfig openAIConfig;
    private SimpMessagingTemplate template;

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

    // POSTMAN
    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageRequest request) {
        template.convertAndSend("/topic/messages", request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // method is called whenever message is send from client to "/app/sendMessage".
    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload MessageRequest request) {

    }

    @SendTo("/topic/messages")
    public MessageResponse broadcastMessage(MessageRequest request) {
        String time = Instant.now().toString();
        return new MessageResponse(request.getFrom(), request.getText(), time);
    }

}
