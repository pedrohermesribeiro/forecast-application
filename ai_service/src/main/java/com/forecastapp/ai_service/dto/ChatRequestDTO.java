package com.forecastapp.ai_service.dto;
import java.util.Map;
// dto/ChatRequest.java
public class ChatRequestDTO {
    private String pergunta;
    private String agentType;        // "SALES_FORECAST" ou "INVESTMENT_ADVISOR"
    private String sessionId;        // opcional (para memória futura)
    private Map<String, Object> context; // risco, objetivo, portfólio etc (futuro)
    public String getPergunta() {
        return pergunta;
    }
    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }
    public String getAgentType() {
        return agentType;
    }
    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public Map<String, Object> getContext() {
        return context;
    }
    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    // getters e setters ou Lombok

    
}
