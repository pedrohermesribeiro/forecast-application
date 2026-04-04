package com.forecastapp.ai_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)  // Não inclui campos nulos no JSON
public class ChatbotResponseDTO {

    // Campos para SALES_FORECAST
    private String explicacao;
    private List<PrevisaoItemDTO> previsao;

    // Campos para INVESTMENT_ADVISOR
    private String resumo;
    private String analise;
    private String recomendacao;
    private String riscos;
    private String disclaimer;

    // Getters e Setters
    public String getExplicacao() { return explicacao; }
    public void setExplicacao(String explicacao) { this.explicacao = explicacao; }

    public List<PrevisaoItemDTO> getPrevisao() { return previsao; }
    public void setPrevisao(List<PrevisaoItemDTO> previsao) { this.previsao = previsao; }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }

    public String getAnalise() { return analise; }
    public void setAnalise(String analise) { this.analise = analise; }

    public String getRecomendacao() { return recomendacao; }
    public void setRecomendacao(String recomendacao) { this.recomendacao = recomendacao; }

    public String getRiscos() { return riscos; }
    public void setRiscos(String riscos) { this.riscos = riscos; }

    public String getDisclaimer() { return disclaimer; }
    public void setDisclaimer(String disclaimer) { this.disclaimer = disclaimer; }
}
