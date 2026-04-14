package com.forecastapp.ai_service.dto;

public class MarketIndicatorDTO {
    private String name;           // "Ibovespa", "Dólar", "IFIX"
    private String symbol;
    private Double value;
    private Double changePercent;  // variação %
    private String changeText;     // " +1,25%" ou "-0,87%"
    private String currency;       // "pts", "R$", "%"
    private String lastUpdate;

    
    
    public MarketIndicatorDTO() {

    }

    public MarketIndicatorDTO(String name, String symbol, Double value, Double changePercent, String changeText,
            String currency, String lastUpdate) {
        this.name = name;
        this.symbol = symbol;
        this.value = value;
        this.changePercent = changePercent;
        this.changeText = changeText;
        this.currency = currency;
        this.lastUpdate = lastUpdate;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
    public Double getChangePercent() {
        return changePercent;
    }
    public void setChangePercent(Double changePercent) {
        this.changePercent = changePercent;
    }
    public String getChangeText() {
        return changeText;
    }
    public void setChangeText(String changeText) {
        this.changeText = changeText;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getLastUpdate() {
        return lastUpdate;
    }
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    
}
