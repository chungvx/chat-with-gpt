package com.example.service.config;

import com.example.service.model.gemini.ChatGeminiRequest;
import com.example.service.model.gemini.ChatGeminiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class GeminiAIConfig {
    @Value("${gemini.model}")
    private String model;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Autowired
    @Qualifier("custom-base-api")
    private final BaseApiCall baseApiCall;

    public GeminiAIConfig(BaseApiCall baseApiCall) {
        this.baseApiCall = baseApiCall;
    }

    public ChatGeminiResponse sendToGemini(String prompt) {
        ChatGeminiRequest request = new ChatGeminiRequest(prompt);
        try {
            var headers = getHeaders();
            String responseTxt = baseApiCall.post(String.format(apiUrl, model, geminiApiKey), headers, null, new HashMap<>(), JsonUtils.marshal(request));
            return JsonUtils.unmarshal(responseTxt, ChatGeminiResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        //headers.add("Authorization", "Bearer " + openaiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }
}