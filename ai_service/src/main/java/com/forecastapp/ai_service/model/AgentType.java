package com.forecastapp.ai_service.model;

public enum AgentType {
    SALES_FORECAST("Analista de Vendas e Previsão"),
    INVESTMENT_ADVISOR("Assessor de Investimentos");

    private final String description;

    AgentType(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
}
