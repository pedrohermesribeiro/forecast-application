package com.forecastapp.ai_service.service;

import java.util.ArrayList;
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
import com.forecastapp.ai_service.model.AiLog;
import com.forecastapp.ai_service.repository.AiLogRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChatbotService {

    private static final Logger log = LoggerFactory.getLogger(ChatbotService.class);
    private final AiLogRepository aiLogRepository;
    private final RestTemplate restTemplate;

    @Value("${GEMINI_API_KEY}")
    private String geminiApiKey;

   // @Value("${GEMINI_API_URL}")
    private String geminiApiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent";

    public ChatbotService(AiLogRepository aiLogRepository, RestTemplate restTemplate) {
        this.aiLogRepository = aiLogRepository;
        this.restTemplate = restTemplate;
    }

    public ChatbotResponseDTO processarPergunta(String pergunta) {
        log.info("📩 Pergunta recebida para processamento: {}", pergunta);

String hoje = java.time.LocalDate.now()
    .plusMonths(1)
    .format(java.time.format.DateTimeFormatter.ofPattern("MMM/yyyy", java.util.Locale.forLanguageTag("pt-BR")))
    .replace(".", ""); // → "Mar/2026" ou "Abr/2026"

        String promptCompleto = """
            Você é um analista de vendas. Responda APENAS com JSON válido, sem texto fora do JSON, sem ```json

            Regras obrigatórias:
            - Campo "explicacao": string com análise clara em português (mínimo 80 palavras e no máximo 100 palavras)
            - Campo "previsao": array EXATAMENTE com 6 itens (nunca 3, 4, 5 ou 7)
            - Cada item deve ter:
            - "mes": string com abreviação de 3 letras em português (Jan, Fev, Mar, Abr, Mai, Jun, Jul, Ago, Set, Out, Nov, Dez)
            - "vendas": número inteiro positivo (sem aspas)
            - A previsão começa no mês seguinte ao atual e vai exatamente 6 meses à frente.
            - Hoje é %s. Comece a previsão em %s.
            - Valores de vendas devem ser plausíveis e variar de forma realista.
            - No final da explicação colocar as referências da explicação e da previsão.

            Pergunta do usuário: %s

            Resposta esperada (exemplo de formato, não copie os valores):
            {
            "explicacao": "Texto da análise aqui...",
            "previsao": [
                {"mes": "Mar", "vendas": 52000},
                {"mes": "Abr", "vendas": 55000},
                {"mes": "Mai", "vendas": 48000},
                {"mes": "Jun", "vendas": 61000},
                {"mes": "Jul", "vendas": 59000},
                {"mes": "Ago", "vendas": 64000}
            ]
            }
            """.formatted(hoje.replaceFirst("(?<=/)[0-9]{4}$", ""), hoje, pergunta);

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
                                String limpo = (String) parts.get(0).get("text");
                                limpo = limpo.replace("```json", "").replace("```", "").trim();
                                try {
                                    limpo = (String) parts.get(0).get("text");
                                    limpo = limpo.replace("```json", "").replace("```", "").trim();
                                    
                                    ObjectMapper mapper = new ObjectMapper();
                                    JsonNode root = mapper.readTree(limpo);

                                    String explicacao = root.path("explicacao").asText(null); // mais seguro que .asText()

                                    // Parse do array "previsao" corretamente
                                    JsonNode previsaoNode = root.path("previsao");
                                    List<ChatbotResponseDTO.PrevisaoDTO> previsaoList = new ArrayList<>();

                                    if (previsaoNode.isArray()) {
                                        for (JsonNode node : previsaoNode) {
                                            ChatbotResponseDTO.PrevisaoDTO dto = new ChatbotResponseDTO.PrevisaoDTO();
                                            dto.setMes(node.path("mes").asText(null));
                                            dto.setVendas(node.path("vendas").asInt(0)); // default 0 se falhar
                                            previsaoList.add(dto);
                                        }
                                    }

                                    responseDTO = new ChatbotResponseDTO(explicacao, previsaoList);

                                    System.err.println("Previsão parseada: " + previsaoList.size() + " itens");

                                } catch (Exception e) {
                                    log.error("Erro ao parsear JSON da Gemini: {}", e.getMessage(), e);
                                    responseDTO = new ChatbotResponseDTO();
                                    responseDTO.setExplicacao(limpo != null ? limpo : "Erro ao processar resposta da IA.");
                                }
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
        System.err.println("ResponseDTO: " + responseDTO.getExplicacao());
        return responseDTO;
    }
}


