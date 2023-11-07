package com.example.service.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.example.service.config.OpenAIConfig;
import com.example.service.model.chat.MessageRequest;
import com.example.service.model.chat.MessageResponse;
import com.example.service.model.gpt.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Slf4j
@RestController
public class ChatController {
    private final OpenAIConfig openAIConfig;

    private final SocketIOServer socketServer;

    public ChatController(OpenAIConfig openAIConfig, SocketIOServer socketServer) {
        this.openAIConfig = openAIConfig;
        this.socketServer = socketServer;
        this.socketServer.addEventListener("chat", MessageRequest.class, onSendMessage);
    }

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

    public DataListener<MessageRequest> onSendMessage = new DataListener<MessageRequest>() {
        @Override
        public void onData(SocketIOClient socketIOClient, MessageRequest request, AckRequest ackRequest) throws Exception {
            /**
             * Sending message to target user
             * target user should subscribe the socket event with his/her name.
             * Send the same payload to user
             */
            log.info("receive message from client " + socketIOClient.getSessionId() + ", message: " + request.getText());
            String time = Instant.now().toString();
            var response = new MessageResponse(request.getFrom(), request.getText(), time);
            String BROAD_CAST = "allMessages"; // same as room_id, user_id who receives
            socketServer.getBroadcastOperations().sendEvent(BROAD_CAST, response);

            /**
             * After sending message to target user we can send acknowledge to sender
             */
            //ackRequest.sendAckData("Message send to target user successfully");
        }
    };
}
