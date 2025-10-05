package com.forecastapp.ai_service.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;

import com.forecastapp.ai_service.dto.ChatbotResponseDTO;
import com.forecastapp.ai_service.service.ChatbotService;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8085"})
@RestController
@RequestMapping("ai")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatbotResponseDTO> chat(@RequestBody Map<String, String> request) {
        String pergunta = request.get("message");
        System.err.println("Pergunta: " + pergunta);
        ChatbotResponseDTO respDTO = new ChatbotResponseDTO();
        respDTO = chatbotService.processarPergunta(pergunta);
        return ResponseEntity.ok(respDTO);
    }
}
