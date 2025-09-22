package com.forecastapp.ai_service.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.forecastapp.ai_service.dto.ChatbotResponseDTO;
import com.forecastapp.ai_service.dto.ChatbotResponseDTO.PrevisaoDTO;
import com.forecastapp.ai_service.model.AiLog;
import com.forecastapp.ai_service.repository.AiLogRepository;

@Service
public class ChatbotService {

    private static final Logger log = LoggerFactory.getLogger(ChatbotService.class);
    private final AiLogRepository aiLogRepository;
    private final RestTemplate restTemplate;

    @Value("${GEMINI_API_KEY}")
    private String geminiApiKey;

    @Value("${GEMINI_API_URL}")
    private String geminiApiUrl;

    public ChatbotService(AiLogRepository aiLogRepository, RestTemplate restTemplate) {
        this.aiLogRepository = aiLogRepository;
        this.restTemplate = restTemplate;
    }

    public ChatbotResponseDTO processarPergunta(String pergunta) {
        log.info("📩 Pergunta recebida para processamento: {}", pergunta);

        String promptCompleto = "Análise e previsão de vendas. Responda com texto e dados para um gráfico.\n" +
                                "Instruções:\n" +
                                "1. A resposta deve ser um JSON válido.\n" +
                                "2. Inclua o campo 'explicacao' com a análise em texto.\n" +
                                "3. Inclua o campo 'previsao' com um array de 3 objetos, cada um com 'mes' e 'vendas' para uma previsão de 3 meses. Assegure-se que o nome da chave dos meses esteja em português, como 'Out', 'Nov', 'Dez', etc. e as vendas sejam valores numéricos.\n" +
                                "4. Use a pergunta do usuário como base: \"" + pergunta + "\".\n" +
                                "5. Não inclua nenhum outro texto ou formatação fora do JSON.";

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        Map<String, String> part = new HashMap<>();
        part.put("text", promptCompleto);
        content.put("parts", Collections.singletonList(part));
        requestBody.put("contents", Collections.singletonList(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String respostaDaIA = "Não foi possível gerar a resposta.";
        ChatbotResponseDTO responseDTO = null;
        
        try {
            String url = String.format("%s?key=%s", geminiApiUrl, geminiApiKey);
            Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);
            
            if (response != null && response.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> candidate = candidates.get(0);
                    if (candidate.containsKey("content")) {
                        Map<String, Object> contentData = (Map<String, Object>) candidate.get("content");
                        if (contentData.containsKey("parts")) {
                            List<Map<String, Object>> parts = (List<Map<String, Object>>) contentData.get("parts");
                            if (!parts.isEmpty()) {
                            	System.err.println("parts da explicação: " + (String) parts.get(0).get("text"));
                            	responseDTO = (ChatbotResponseDTO) new ChatbotResponseDTO((String) parts.get(0).get("text"),parts.getLast());
                                respostaDaIA = (String) parts.get(0).get("text");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("❌ Erro ao chamar a API do Gemini: {}", e.getMessage());
        }

        AiLog logEntry = new AiLog();
        logEntry.setPergunta(pergunta);
        logEntry.setResposta(respostaDaIA);
        aiLogRepository.save(logEntry);
        
        

        log.info("💾 Interação salva em tb_ai_logs (id: {})", logEntry.getId());
        log.info("✅ Resposta da IA: {}", respostaDaIA);

        return responseDTO;
    }
}


