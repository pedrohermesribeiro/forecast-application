package com.forecastapp.a.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChatbotInitializer {

    private static final Logger log = LoggerFactory.getLogger(ChatbotInitializer.class);

    @PostConstruct
    public void init() {
        log.info("🚀 Chatbot AI Service inicializado.");
        log.info("👉 Endpoint disponível: POST /ai/chat");
    }
}

