package com.forecastapp.ai_service.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// import org.apache.el.stream.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.forecastapp.ai_service.dto.ChatbotResponseDTO;
import com.forecastapp.ai_service.model.AiLog;
import com.forecastapp.ai_service.repository.AiLogRepository;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
//import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChatbotService {

    private static final Logger log = LoggerFactory.getLogger(ChatbotService.class);
    private final AiLogRepository aiLogRepository;
    ///private final RestTemplate restTemplate;

    private OpenAIClient client;

    @Value("")
    private String geminiApiKey;

   // @Value("")
    // private String geminiApiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent";

        // Lê a chave do application.properties (openai.api.key)
    // public void AIService(@Value("${openai.api.key}") String apiKey) {
    //     this.client = OpenAIOkHttpClient.builder()
    //         .apiKey(apiKey)
    //         .build();
    // }

    public ChatbotService(@Value("${OPENAI_API_KEY}") String apiKey,AiLogRepository aiLogRepository, RestTemplate restTemplate) {
        this.aiLogRepository = aiLogRepository;
                this.client = OpenAIOkHttpClient.builder()
            .apiKey(apiKey)
            .build();
    }

    @SuppressWarnings("unused")
    public ChatbotResponseDTO processarPergunta(String pergunta) {
        log.info("📩 Pergunta recebida para processamento: {}", pergunta);

        String hoje = java.time.LocalDate.now()
            .plusMonths(1)
            .format(java.time.format.DateTimeFormatter.ofPattern("MMM/yyyy", java.util.Locale.forLanguageTag("pt-BR")))
            .replace(".", ""); // → "Mar/2026" ou "Abr/2026"

        String promptCompleto = """
            Você é um analista de vendas. Responda APENAS com JSON válido, sem texto fora do JSON, sem ```json

            Regras obrigatórias:
            - Campo "explicacao": string com análise clara em português (mínimo 120 palavras e no máximo 150 palavras)
            - Campo "previsao": array EXATAMENTE com 6 itens (nunca 3, 4, 5 ou 7)
            - Cada item deve ter:
            - "mes": string com abreviação de 3 letras em português (Jan, Fev, Mar, Abr, Mai, Jun, Jul, Ago, Set, Out, Nov, Dez)
            - "vendas": número inteiro positivo (sem aspas)
            - A previsão começa no mês seguinte ao atual e vai exatamente 6 meses à frente.
            - Hoje é %s. Comece a previsão em %s.
            - Valores de vendas devem ser plausíveis e variar de forma realista.
            - No final da explicação colocar as referências e colocar o link das referências da explicação e da previsão.

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

        ChatbotResponseDTO responseDTO = null;
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
        	.model("gpt-4o-mini")
            //.model(ChatModel.O3_MINI)       // modelo mais barato
            .addUserMessage(promptCompleto + hoje)
            //.maxTokens(32)                 // é só um lance curto
            .build();
        ChatCompletion completion = client.chat().completions().create(params);
        String respostaDaIA = completion.choices().get(0).message().content().orElse("Sem resposta");
        System.err.println("Previsão parseada RespostaDaIA: " + respostaDaIA);
        
        try {
                ObjectMapper mapper = new ObjectMapper();
    
                responseDTO = mapper.readValue(respostaDaIA, ChatbotResponseDTO.class);
            
        } catch (Exception e) {
            log.error("❌ Erro ao chamar a API do Gemini: {}", e.getMessage());
        }

        AiLog logEntry = new AiLog();
        logEntry.setPergunta(pergunta);
        logEntry.setResposta(responseDTO.getExplicacao());
        aiLogRepository.save(logEntry);
        
        

        log.info("💾 Interação salva em tb_ai_logs (id: {})", logEntry.getId());
        log.info("✅ Resposta da IA: {}", respostaDaIA);
        System.err.println("ResponseDTO: " + responseDTO.getExplicacao());
        return responseDTO;
    }
}


