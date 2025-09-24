package com.forecastapp.ai_service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forecastapp.ai_service.dto.ChatbotResponseDTO;
import com.forecastapp.ai_service.service.ChatbotService;

@RestController
@RequestMapping("/ai")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody Map<String, String> request) {
        String pergunta = request.get("pergunta");
        ChatbotResponseDTO respDTO = new ChatbotResponseDTO();
        respDTO = chatbotService.processarPergunta(pergunta);
        return respDTO.getExplicacao();
    }
}
