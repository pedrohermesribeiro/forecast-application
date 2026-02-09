package com.forecastapp.ai_service.dto;

public class ChatTokenResponse {
    String token;
    ChatbotResponseDTO resposta;
    public String getToken() {
        return token;
    }
    public ChatbotResponseDTO getResposta() {
        return resposta;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setResposta(ChatbotResponseDTO resposta) {
        this.resposta = resposta;
    }
    public ChatTokenResponse(String token, ChatbotResponseDTO resposta) {
        this.token = token;
        this.resposta = resposta;
    }
    public ChatTokenResponse() {
    }

    



}
