package com.forecastapp.ai_service.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.forecastapp.ai_service.dto.MarketIndicatorDTO;

import ch.qos.logback.classic.Logger;

// import io.jsonwebtoken.lang.Collections;

@Service
public class QuoteService {

    private static final Logger log = (Logger) LoggerFactory.getLogger(QuoteService.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BRAPI_URL = "https://brapi.dev/api/quote/";

    public List<MarketIndicatorDTO> getMainIndicators() {
        String tickers = "^BVSP,USDBRL,IFIX";   // Ibovespa, Dólar, IFIX
        String url = BRAPI_URL + tickers;

        try {
            log.info("Buscando indicadores: {}", url);

            // Usamos LinkedHashMap para maior segurança
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                log.warn("Resposta nula da brapi.dev");
                return Collections.emptyList();
            }

            Object resultsObj = response.get("results");

            if (resultsObj instanceof List) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> results = (List<Map<String, Object>>) resultsObj;

                return results.stream()
                        .map(this::convertToIndicator)
                        .filter(Objects::nonNull)   // remove possíveis nulos
                        .collect(Collectors.toList());
            } else {
                log.error("Formato inesperado da resposta da brapi. 'results' não é uma lista.");
                return Collections.emptyList();
            }

        } catch (Exception e) {
            log.error("Erro ao buscar indicadores financeiros da brapi.dev", e);  // ← Mudança importante
            // Não loga só a mensagem, mas toda a stack trace (muito mais útil)
            return Collections.emptyList();
        }
    }

    private MarketIndicatorDTO convertToIndicator(Map<String, Object> data) {
        try {
            if (data == null) return null;

            String symbol = (String) data.get("symbol");
            if (symbol == null) return null;

            MarketIndicatorDTO ind = new MarketIndicatorDTO();
            ind.setSymbol(symbol);
            ind.setValue(getDouble(data, "regularMarketPrice"));
            ind.setChangePercent(getDouble(data, "regularMarketChangePercent"));

            // Nome amigável
            switch (symbol.toUpperCase()) {
                case "^BVSP" -> {
                    ind.setName("Ibovespa");
                    ind.setCurrency("pts");
                }
                case "USDBRL" -> {
                    ind.setName("Dólar Comercial");
                    ind.setCurrency("R$");
                }
                case "IFIX" -> {
                    ind.setName("IFIX");
                    ind.setCurrency("pts");
                }
                default -> ind.setName(symbol);
            }

            double change = ind.getChangePercent() != null ? ind.getChangePercent() : 0.0;
            ind.setChangeText((change >= 0 ? "+ " : "") + String.format("%.2f", change) + "%");

            return ind;

        } catch (Exception e) {
            log.warn("Erro ao converter indicador para símbolo: {}", data.get("symbol"), e);
            return null;
        }
    }

    // Método auxiliar seguro para evitar NullPointer e ClassCast
    private Double getDouble(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return null;
    }
}
