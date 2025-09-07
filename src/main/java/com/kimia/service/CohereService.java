package com.kimia.service;

import com.cohere.api.Cohere;
import com.cohere.api.core.CohereException;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.ChatMessage;
import com.cohere.api.types.Message;
import java.util.List;

public class CohereService {
    private final Cohere cohere;

    public CohereService() {
        String clientKey = "JfAyy5xUbUkLaB1D5Rr9a4DPIH7ocanS24nqbb4N";
        System.out.println("Initializing Cohere client...");
        this.cohere = Cohere.builder().token(clientKey).clientName("Kimia").build();
    }

    public String generateResume(String info, String prompt) {
        System.out.println("Sending resume generation request to Cohere...");
        try {
            return cohere.chat(ChatRequest.builder()
                    .message(info)
                    .chatHistory(List.of(
                            Message.user(ChatMessage.builder().message("Here is the information I have about this person:").build()),
                            Message.user(ChatMessage.builder().message(prompt).build())
                    ))
                    .build()).getText();
        } catch (CohereException exception) {
            throw new CohereException("Failed to connect to cohere servers: Connect you VPN before running this application.");
        }
    }

    public String generateFileName(String info) {
        return cohere.chat(ChatRequest.builder()
                .message("What is this person's snake-cased full name in English? Do not include any commentary.")
                .chatHistory(List.of(Message.user(ChatMessage.builder().message(info).build())))
                .build()).getText();
    }
}