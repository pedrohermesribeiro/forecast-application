// package com.forecastapp.ai_service.controller;

// //import java.util.HashMap;
// import java.util.Map;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// //import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// //import org.springframework.util.DigestUtils;
// //import org.springframework.web.bind.annotation.CrossOrigin;
// //import org.apache.commons.codec.digest.DigestUtils;
// import com.forecastapp.ai_service.util.HashUtil;
// import com.forecastapp.ai_service.dto.ChatRequestDTO;
// import com.forecastapp.ai_service.dto.ChatTokenResponse;
// import com.forecastapp.ai_service.dto.ChatbotResponseDTO;
// import com.forecastapp.ai_service.security.JwtTokenUtil;
// import com.forecastapp.ai_service.service.ChatbotService;

// //@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8085", "https://forecast-frontend-gm1d.onrender.com"})
// @RestController
// @RequestMapping("/ai")
// public class ChatbotController {

//     private final ChatbotService chatbotService;

//     @Autowired
//     private JwtTokenUtil jwtTokenUtil;

//     public ChatbotController(ChatbotService chatbotService) {
//         this.chatbotService = chatbotService;
//     }

    // @PostMapping("/chat")
    // public ResponseEntity<ChatTokenResponse> chat(@RequestBody Map<String, String> request) {

    //     System.out.println("🔥 RECEBIDO NO AI_SERVICE: " + request.get("pergunta"));
    //     String pergunta = request.get("pergunta");
    //     System.err.println("Pergunta: " + pergunta);
    //     ChatbotResponseDTO respDTO = new ChatbotResponseDTO();
    //     respDTO = chatbotService.processarPergunta(pergunta);
    //     System.out.println("➡️ Chamando URL request: 3" + request);
    //     String hash = HashUtil.sha256(respDTO.getExplicacao());
    //     String token = jwtTokenUtil.generateToken(hash);
    //     //String token = jwtTokenUtil.generateToken(respDTO.getExplicacao());
    //     ChatTokenResponse chatResp = new ChatTokenResponse();
    //     chatResp.setResposta(respDTO);
    //     chatResp.setToken(token);

    //     return ResponseEntity.ok(chatResp);
    // }

//     @PostMapping("/chat")
//     public ResponseEntity<ChatTokenResponse> chat(@RequestBody ChatRequestDTO request) {
//         System.out.println("🔥 Agente solicitado: " + request.getAgentType());

//         ChatbotResponseDTO respDTO = chatbotService.processarPergunta(
//             request.getPergunta(), 
//             request.getAgentType()
//         );

//         // ... resto do código de hash + token igual
//         String hash = HashUtil.sha256(respDTO.getExplicacao());
//         String token = jwtTokenUtil.generateToken(hash);

//         ChatTokenResponse chatResp = new ChatTokenResponse();
//         chatResp.setResposta(respDTO);
//         chatResp.setToken(token);

//         return ResponseEntity.ok(chatResp);
//     }
// }

package com.forecastapp.ai_service.controller;

import com.forecastapp.ai_service.dto.ChatRequestDTO;
import com.forecastapp.ai_service.dto.ChatTokenResponse;
import com.forecastapp.ai_service.dto.ChatbotResponseDTO;
import com.forecastapp.ai_service.dto.MarketIndicatorDTO;
import com.forecastapp.ai_service.service.ChatbotService;
import com.forecastapp.ai_service.service.QuoteService;
import com.forecastapp.ai_service.security.JwtTokenUtil;
import com.forecastapp.ai_service.util.HashUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class ChatbotController {

    private final ChatbotService chatbotService;

    private final QuoteService quoteService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
        this.quoteService = new QuoteService();
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatTokenResponse> chat(@RequestBody ChatRequestDTO request) {
        System.out.println("🔥 Agente solicitado: " + request.getAgentType() + " | Pergunta: " + request.getPergunta());

        ChatbotResponseDTO respDTO = chatbotService.processarPergunta(
                request.getPergunta(),
                request.getAgentType()
        );

        // Gera hash usando o campo principal da resposta
        String textoParaHash = respDTO.getExplicacao() != null 
                ? respDTO.getExplicacao() 
                : respDTO.getResumo();

        String hash = HashUtil.sha256(textoParaHash != null ? textoParaHash : "");
        String token = jwtTokenUtil.generateToken(hash);

        ChatTokenResponse chatResp = new ChatTokenResponse();
        chatResp.setResposta(respDTO);
        chatResp.setToken(token);

        return ResponseEntity.ok(chatResp);
    }

    @GetMapping("/market/indicators")
    public ResponseEntity<List<MarketIndicatorDTO>> getIndicators() {
        List<MarketIndicatorDTO> indicators = quoteService.getMainIndicators();
        return ResponseEntity.ok(indicators);
    }
}