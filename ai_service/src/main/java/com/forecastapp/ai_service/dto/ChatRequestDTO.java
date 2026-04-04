package com.forecastapp.ai_service.dto;

public class ChatRequestDTO {
    private String pergunta;
    private String agentType;  // SALES_FORECAST ou INVESTMENT_ADVISOR

    // Getters e Setters
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
}