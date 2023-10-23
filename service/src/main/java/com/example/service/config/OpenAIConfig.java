package com.example.service.config;

import com.example.service.model.gpt.ChatRequest;
import com.example.service.model.gpt.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
public class OpenAIConfig {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Autowired
    @Qualifier("custom-base-api")
    private final BaseApiCall baseApiCall;

    public OpenAIConfig(BaseApiCall baseApiCall) {
        this.baseApiCall = baseApiCall;
    }

    public ChatResponse sendToGpt(String prompt) {
        ChatRequest request = new ChatRequest(model, prompt);
        try {
            var headers = getHeaders();
            String responseTxt = baseApiCall.post(apiUrl, headers, null, new HashMap<>(), JsonUtils.marshal(request));
            return JsonUtils.unmarshal(responseTxt, ChatResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + openaiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }
}