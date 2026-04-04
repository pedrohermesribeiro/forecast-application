package com.forecastapp.ai_service.dto;

public class PrevisaoItemDTO {
    private String mes;
    private int vendas;
    private String taxa;   // pode ser "5" ou "-10"

    // Getters e Setters
    public String getMes() { return mes; }
    public void setMes(String mes) { this.mes = mes; }

    public int getVendas() { return vendas; }
    public void setVendas(int vendas) { this.vendas = vendas; }

    public String getTaxa() { return taxa; }
    public void setTaxa(String taxa) { this.taxa = taxa; }
}
