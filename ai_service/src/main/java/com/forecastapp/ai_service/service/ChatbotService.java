// package com.forecastapp.ai_service.service;

// import java.util.Collections;
// import java.util.HashMap;
// import java.util.Map;

// // import org.apache.el.stream.Optional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

// import com.forecastapp.ai_service.dto.ChatbotResponseDTO;
// import com.forecastapp.ai_service.model.AgentType;
// import com.forecastapp.ai_service.model.AiLog;
// import com.forecastapp.ai_service.repository.AiLogRepository;
// import com.openai.client.OpenAIClient;
// import com.openai.client.okhttp.OpenAIOkHttpClient;
// import com.openai.models.chat.completions.ChatCompletion;
// import com.openai.models.chat.completions.ChatCompletionCreateParams;
// //import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;

// @Service
// public class ChatbotService {

//     private static final Logger log = LoggerFactory.getLogger(ChatbotService.class);
//     private final AiLogRepository aiLogRepository;
//     ///private final RestTemplate restTemplate;

//     private OpenAIClient client;

//     @Value("")
//     private String geminiApiKey;

//    // @Value("")
//     // private String geminiApiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent";

//         // Lê a chave do application.properties (openai.api.key)
//     // public void AIService(@Value("${openai.api.key}") String apiKey) {
//     //     this.client = OpenAIOkHttpClient.builder()
//     //         .apiKey(apiKey)
//     //         .build();
//     // }

//     public ChatbotService(@Value("${OPENAI_API_KEY}") String apiKey,AiLogRepository aiLogRepository, RestTemplate restTemplate) {
//         this.aiLogRepository = aiLogRepository;
//                 this.client = OpenAIOkHttpClient.builder()
//             .apiKey(apiKey)
//             .build();
//     }

//     @SuppressWarnings("unused")
//     public ChatbotResponseDTO processarPergunta(String pergunta) {
//         log.info("📩 Pergunta recebida para processamento: {}", pergunta);

//         String hoje = java.time.LocalDate.now()
//             .plusMonths(1)
//             .format(java.time.format.DateTimeFormatter.ofPattern("MMM/yyyy", java.util.Locale.forLanguageTag("pt-BR")))
//             .replace(".", ""); // → "Mar/2026" ou "Abr/2026"

//         String promptCompleto = """
//             Você é um analista de vendas. Responda APENAS com JSON válido, sem texto fora do JSON, sem ```json

//             Regras obrigatórias:
//             - Campo "explicacao": string com análise clara em português (mínimo 400 palavras e no máximo 450 palavras)
//             - ainda na explicação comente sobre os possíveis motivos da taxa de crescimento sejam eles positivos que proporcionaram elevação
//               nas vendas ou fatos negativos que ocasionaram diminuição nas vendas.
//             - Campo "previsao": array EXATAMENTE com 6 itens (nunca 3, 4, 5 ou 7)
//             - Na previsão cite os pontos fortes e fracos da empresa responsável pelo produto.
//             - Na previsão fale da sazonalidade anual do produto.
//             - Cada item deve ter:
//             - "mes": string com abreviação de 3 letras em português (Jan, Fev, Mar, Abr, Mai, Jun, Jul, Ago, Set, Out, Nov, Dez)
//             - "vendas": número inteiro positivo que represente a previsão de venda daquele mês (sem aspas)
//             - "taxa": na previsão no json no valor "taxa" voce coloca número inteiro positivo ou negativo que represente a taxa de crescimento das venda
//                daquele mês (sem aspas), não precisa colar o símbolo da prcentagem só o numero inteiro positivo ou negativo.
//             - A previsão começa no mês seguinte ao atual e vai exatamente 6 meses à frente.
//             - Hoje é %s. Comece a previsão em %s.
//             - Valores de vendas devem ser plausíveis e variar de forma realista.
//             - No final da explicação colocar as referências e colocar o link das referências da explicação e da previsão.
//             - os valores colocados no objeto json abaixa são ficticio, serve como exemplos, preciso de uma previsão  atual das vendas
//               do produto e da taxa de crescimento das vendas do produto que vai ser pesquisado, coloque também a taxa de crescimento 
//               das vendas do produto pesquisado no campo do json "taxa".
//             Pergunta do usuário: %s

//             Resposta esperada (exemplo de formato, não copie os valores):
//             {
//             "explicacao": "Texto da análise aqui...",
//             "previsao": [
//                 {"mes": "Mar", "vendas": 52000, "taxa": "5"},
//                 {"mes": "Abr", "vendas": 55000, "taxa": "-10"},
//                 {"mes": "Mai", "vendas": 48000, "taxa": "-10"},
//                 {"mes": "Jun", "vendas": 61000, "taxa": " 10"},
//                 {"mes": "Jul", "vendas": 59000, "taxa": " 1"},
//                 {"mes": "Ago", "vendas": 64000, "taxa": " 10"}
//             ]
//             }
//             """.formatted(hoje.replaceFirst("(?<=/)[0-9]{4}$", ""), hoje, pergunta);

//         ChatbotResponseDTO responseDTO = null;
//         ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
//         	.model("gpt-5.1")
//             //.model(ChatModel.O3_MINI)       // modelo mais barato
//             .addUserMessage(promptCompleto + hoje)
//             //.maxTokens(32)                 // é só um lance curto
//             .build();
//         ChatCompletion completion = client.chat().completions().create(params);
//         String respostaDaIA = completion.choices().get(0).message().content().orElse("Sem resposta");
//         System.err.println("Previsão parseada RespostaDaIA: " + respostaDaIA);
        
//         try {
//                 ObjectMapper mapper = new ObjectMapper();
    
//                 responseDTO = mapper.readValue(respostaDaIA, ChatbotResponseDTO.class);
            
//         } catch (Exception e) {
//             log.error("❌ Erro ao chamar a API do Gemini: {}", e.getMessage());
//         }

//         AiLog logEntry = new AiLog();
//         logEntry.setPergunta(pergunta);
//         logEntry.setResposta(responseDTO.getExplicacao());
//         aiLogRepository.save(logEntry);
        
        

//         log.info("💾 Interação salva em tb_ai_logs (id: {})", logEntry.getId());
//         log.info("✅ Resposta da IA: {}", respostaDaIA);
//         System.err.println("ResponseDTO: " + responseDTO.getExplicacao());
//         return responseDTO;
//     }
// }

// @Service
// public class ChatbotService {

//     private static final Logger log = LoggerFactory.getLogger(ChatbotService.class);
//     private final AiLogRepository aiLogRepository;
//     ///private final RestTemplate restTemplate;
//     private OpenAIClient client;

//     private final Map<AgentType, String> promptTemplates = new HashMap<>();

//     public ChatbotService(@Value("${OPENAI_API_KEY}") String apiKey, AiLogRepository aiLogRepository) {
//         this.aiLogRepository = aiLogRepository;
//         this.client = OpenAIOkHttpClient.builder().apiKey(apiKey).build();

//         // Carrega os templates
//         loadPromptTemplates();
//     }

//     private void loadPromptTemplates() {
//         // Prompt atual de Vendas (já existente)
//         promptTemplates.put(AgentType.SALES_FORECAST, buildSalesPrompt());

//         // Novo prompt de Investimentos (vamos criar agora)
//         promptTemplates.put(AgentType.INVESTMENT_ADVISOR, buildInvestmentPrompt());
//     }

//     private String buildSalesPrompt() {
//         // Coloque aqui todo o seu prompt atual de vendas (o que está no processarPergunta)
//         return """ 
//             Você é um analista de vendas. Responda APENAS com JSON válido, sem texto fora do JSON, sem ```json

//             Regras obrigatórias:
//             - Campo "explicacao": string com análise clara em português (mínimo 400 palavras e no máximo 450 palavras)
//             - ainda na explicação comente sobre os possíveis motivos da taxa de crescimento sejam eles positivos que proporcionaram elevação
//               nas vendas ou fatos negativos que ocasionaram diminuição nas vendas.
//             - Campo "previsao": array EXATAMENTE com 6 itens (nunca 3, 4, 5 ou 7)
//             - Na previsão cite os pontos fortes e fracos da empresa responsável pelo produto.
//             - Na previsão fale da sazonalidade anual do produto.
//             - Cada item deve ter:
//             - "mes": string com abreviação de 3 letras em português (Jan, Fev, Mar, Abr, Mai, Jun, Jul, Ago, Set, Out, Nov, Dez)
//             - "vendas": número inteiro positivo que represente a previsão de venda daquele mês (sem aspas)
//             - "taxa": na previsão no json no valor "taxa" voce coloca número inteiro positivo ou negativo que represente a taxa de crescimento das venda
//                daquele mês (sem aspas), não precisa colar o símbolo da prcentagem só o numero inteiro positivo ou negativo.
//             - A previsão começa no mês seguinte ao atual e vai exatamente 6 meses à frente.
//             - Hoje é %s. Comece a previsão em %s.
//             - Valores de vendas devem ser plausíveis e variar de forma realista.
//             - No final da explicação colocar as referências e colocar o link das referências da explicação e da previsão.
//             - os valores colocados no objeto json abaixa são ficticio, serve como exemplos, preciso de uma previsão  atual das vendas
//               do produto e da taxa de crescimento das vendas do produto que vai ser pesquisado, coloque também a taxa de crescimento 
//               das vendas do produto pesquisado no campo do json "taxa".
//             Pergunta do usuário: %s

//             Resposta esperada (exemplo de formato, não copie os valores):
//             {
//             "explicacao": "Texto da análise aqui...",
//             "previsao": [
//                 {"mes": "Mar", "vendas": 52000, "taxa": "5"},
//                 {"mes": "Abr", "vendas": 55000, "taxa": "-10"},
//                 {"mes": "Mai", "vendas": 48000, "taxa": "-10"},
//                 {"mes": "Jun", "vendas": 61000, "taxa": " 10"},
//                 {"mes": "Jul", "vendas": 59000, "taxa": " 1"},
//                 {"mes": "Ago", "vendas": 64000, "taxa": " 10"}
//             ]
//             }
//             """;
//     }

//     private String buildInvestmentPrompt() {
//         return """
//             Você é um **Assessor de Investimentos Certificado (CFA-like)** brasileiro, extremamente competente, conservador e transparente.
            
//             Regras obrigatórias:
//             - Responda SEMPRE em português do Brasil, linguagem clara e profissional.
//             - Seja honesto: nunca dê garantia de rentabilidade, sempre mencione riscos.
//             - Use dados reais do mercado brasileiro quando possível (Ibovespa, Selic, CDI, inflação, dólar etc.).
//             - Estrutura da resposta:
//               1. Resumo da análise (máximo 150 palavras)
//               2. Análise detalhada
//               3. Recomendações práticas (alocação sugerida, ativos, prazos)
//               4. Riscos principais
//               5. Disclaimer obrigatório no final
            
//             Contexto atual: Hoje é {data_atual}.
//             Pergunta do usuário: {pergunta}
            
//             Responda em formato JSON com os campos:
//             {
//               "resumo": "...",
//               "analise": "...",
//               "recomendacao": "...",
//               "riscos": "...",
//               "disclaimer": "Lembrete: Isso não é recomendação de investimento..."
//             }
//             """;
//     }

//     public ChatbotResponseDTO processarPergunta(String pergunta, String agentTypeStr) {

//         ChatbotResponseDTO responseDTO = null;

//         AgentType agentType = AgentType.valueOf(agentTypeStr.toUpperCase());

//         String template = promptTemplates.getOrDefault(agentType, promptTemplates.get(AgentType.SALES_FORECAST));

//         String dataAtual = java.time.LocalDate.now()
//                 .format(java.time.format.DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new java.util.Locale("pt", "BR")));

//         String promptCompleto = template
//                 .replace("{data_atual}", dataAtual)
//                 .replace("{pergunta}", pergunta);
//         //return null;
//         String hoje = java.time.LocalDate.now()
//             .plusMonths(1)
//             .format(java.time.format.DateTimeFormatter.ofPattern("MMM/yyyy", java.util.Locale.forLanguageTag("pt-BR")))
//             .replace(".", ""); // → "Mar/2026" ou "Abr/2026"

//         ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
//         	.model("gpt-5.1")
//             //.model(ChatModel.O3_MINI)       // modelo mais barato
//             .addUserMessage(promptCompleto + hoje)
//             //.maxTokens(32)                 // é só um lance curto
//             .build();
//         ChatCompletion completion = client.chat().completions().create(params);
//         String respostaDaIA = completion.choices().get(0).message().content().orElse("Sem resposta");
//         System.err.println("Previsão parseada RespostaDaIA: " + respostaDaIA);
        
//         try {
//                 ObjectMapper mapper = new ObjectMapper();
    
//                 responseDTO = mapper.readValue(respostaDaIA, ChatbotResponseDTO.class);
            
//         } catch (Exception e) {
//             log.error("❌ Erro ao chamar a API do Gemini: {}", e.getMessage());
//         }

//         AiLog logEntry = new AiLog();
//         logEntry.setPergunta(pergunta);
//         logEntry.setResposta(responseDTO.getExplicacao());
//         aiLogRepository.save(logEntry);
        
        

//         log.info("💾 Interação salva em tb_ai_logs (id: {})", logEntry.getId());
//         log.info("✅ Resposta da IA: {}", respostaDaIA);
//         System.err.println("ResponseDTO: " + responseDTO.getExplicacao());
//         return responseDTO;
//     }
    
// }


package com.forecastapp.ai_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forecastapp.ai_service.dto.ChatbotResponseDTO;
import com.forecastapp.ai_service.model.AgentType;
import com.forecastapp.ai_service.model.AiLog;
import com.forecastapp.ai_service.repository.AiLogRepository;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatbotService {

    private static final Logger log = LoggerFactory.getLogger(ChatbotService.class);

    private final AiLogRepository aiLogRepository;
    private final OpenAIClient client;
    //AgentType agentType = AgentType.; // agentTypeStr.toUpperCase());
    private final Map<AgentType, String> promptTemplates = new HashMap<>();

    public ChatbotService(@Value("${OPENAI_API_KEY}") String apiKey, AiLogRepository aiLogRepository) {
        this.aiLogRepository = aiLogRepository;
        this.client = OpenAIOkHttpClient.builder().apiKey(apiKey).build();
        loadPromptTemplates();
    }

    private void loadPromptTemplates() {
        promptTemplates.put(AgentType.SALES_FORECAST, buildSalesPrompt());
        promptTemplates.put(AgentType.INVESTMENT_ADVISOR, buildInvestmentPrompt());
    }

    private String buildSalesPrompt() {
        return """
            Você é um analista de vendas. Responda APENAS com JSON válido, sem texto fora do JSON, sem ```json
                
            Regras obrigatórias:
            - Campo "explicacao": string com análise clara em português (mínimo 400 palavras e no máximo 450 palavras)
            - Ainda na explicação comente sobre os possíveis motivos da taxa de crescimento (positivos ou negativos).
            - Campo "previsao": array EXATAMENTE com 6 itens.
            - Na previsão cite pontos fortes e fracos da empresa e a sazonalidade anual do produto.
            - Cada item: "mes" (Jan, Fev, etc.), "vendas" (número inteiro), "taxa" (inteiro positivo ou negativo).
            - A previsão começa no mês seguinte ao atual e vai exatamente 6 meses à frente.
            - Hoje é %s. Comece a previsão em %s.
            - Valores plausíveis e realistas.
            - No final da explicação coloque referências com links.
                
            Pergunta do usuário: %s
                
            Responda apenas com o JSON no formato:
            {
              "explicacao": "...",
              "previsao": [
                {"mes": "Abr", "vendas": 52000, "taxa": "5"},
                ...
              ]
            }
            """;
    }

    private String buildInvestmentPrompt() {
        return """
            Você é um Assessor de Investimentos Certificado (CFA-like) brasileiro, conservador e transparente.
            
            Responda SEMPRE em português do Brasil, linguagem clara e profissional.
            Nunca dê garantia de rentabilidade. Sempre mencione riscos.
            
            Estrutura da resposta em JSON:
            {
              "resumo": "resumo curto (máx 150 palavras)",
              "analise": "análise detalhada",
              "recomendacao": "recomendações práticas",
              "riscos": "principais riscos",
              "disclaimer": "Lembrete: Isso não é recomendação de investimento. Consulte um profissional..."
            }
            
            Contexto atual: Hoje é {data_atual}.
            Pergunta do usuário: {pergunta}
            """;
    }

    public ChatbotResponseDTO processarPergunta(String pergunta, String agentTypeStr) {
        AgentType agentType = AgentType.valueOf(agentTypeStr.toUpperCase());

        String template = promptTemplates.getOrDefault(agentType, promptTemplates.get(AgentType.SALES_FORECAST));

        String dataAtual = java.time.LocalDate.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new java.util.Locale("pt", "BR")));

        String promptCompleto = template
                .replace("{data_atual}", dataAtual)
                .replace("{pergunta}", pergunta);

        // Para o prompt de vendas, adiciona a data da previsão
        if (agentType == AgentType.SALES_FORECAST) {
            String hoje = java.time.LocalDate.now()
                    .plusMonths(1)
                    .format(java.time.format.DateTimeFormatter.ofPattern("MMM/yyyy", java.util.Locale.forLanguageTag("pt-BR")))
                    .replace(".", "");
            promptCompleto = String.format(promptCompleto, dataAtual, hoje, pergunta);
        }

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("gpt-5.1")   // ou "gpt-5.1" se você tiver acesso
                .addUserMessage(promptCompleto)
                .build();

        ChatCompletion completion = client.chat().completions().create(params);
        String respostaDaIA = completion.choices().get(0).message().content().orElse("Sem resposta");

        log.info("Resposta bruta da IA: {}", respostaDaIA);

        ChatbotResponseDTO responseDTO = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            responseDTO = mapper.readValue(respostaDaIA, ChatbotResponseDTO.class);
        } catch (Exception e) {
            log.error("Erro ao fazer parse do JSON da IA: {}", e.getMessage());
            // Fallback simples
            responseDTO = new ChatbotResponseDTO();
            responseDTO.setExplicacao("Erro ao processar resposta da IA. Tente novamente.");
        }

        // Salva log (ajustado para ambos os agentes)
        AiLog logEntry = new AiLog();
        logEntry.setPergunta(pergunta);
        logEntry.setResposta(responseDTO.getExplicacao() != null ? responseDTO.getExplicacao() : responseDTO.getResumo());
        aiLogRepository.save(logEntry);

        return responseDTO;
    }
}


