package com.example.service.model.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class ChatGeminiResponse {
    private List<Candidate> candidates;
}
