package com.forecastapp.ai_service.dto;

import java.util.List;
import java.util.Map;

public class ChatbotResponseDTO {
    private String explicacao;
    private List<PrevisaoDTO> previsao;

    // Construtor padrão
    public ChatbotResponseDTO(String explicacao, Map<String, Object> map) {
    	
    	this.explicacao = explicacao;
    	//this.previsao = map;
    	
    }

    // Construtor com todos os campos
    public ChatbotResponseDTO(String explicacao, List<PrevisaoDTO> previsao) {
        this.explicacao = explicacao;
        this.previsao = previsao;
    }
    
    

    public ChatbotResponseDTO() {
		super();
	}

	public String getExplicacao() {
        return explicacao;
    }

    public void setExplicacao(String explicacao) {
        this.explicacao = explicacao;
    }

    public List<PrevisaoDTO> getPrevisao() {
        return previsao;
    }

    public void setPrevisao(List<PrevisaoDTO> previsao) {
        this.previsao = previsao;
    }

    public static class PrevisaoDTO {
        private String mes;
        private int vendas;

        public PrevisaoDTO() {}

        public String getMes() {
            return mes;
        }

        public void setMes(String mes) {
            this.mes = mes;
        }

        public int getVendas() {
            return vendas;
        }

        public void setVendas(int vendas) {
            this.vendas = vendas;
        }
    }
}
