package com.forecastapp.ai_service.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import com.forecastapp.ai_service.dto.ChatbotResponseDTO;
import com.forecastapp.ai_service.service.ChatbotService;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8085"})
@RestController
@RequestMapping("/ai")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/chat")
    public ChatbotResponseDTO chat(@RequestBody Map<String, String> request) {
        System.out.println("🔥 RECEBIDO NO AI_SERVICE: " + request.get("pergunta"));
        String pergunta = request.get("pergunta");
        System.err.println("Pergunta: " + pergunta);
         ChatbotResponseDTO respDTO = new ChatbotResponseDTO();
        respDTO = chatbotService.processarPergunta(pergunta);
        return respDTO;
    }
}
