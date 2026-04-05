import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { jwtDecode } from 'jwt-decode';
import * as CryptoJS from 'crypto-js';
import { PredictionComponent } from '../prediction/prediction.component';


interface ChatMessage {
  sender: 'user' | 'bot';
  text: string;
}

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule, PredictionComponent],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent {
  selectedAgent: string = 'SALES_FORECAST';   // ← Corrigido: começa com um valor válido
  messages: ChatMessage[] = [];
  newMessage: string = '';
  previsao: any[] = [];
  showPrediction: boolean = false;
  loading: boolean = false;

  // Variáveis para o agente de Investimentos (agora como string, não array)
  resumo: string = '';
  analise: string = '';
  recomendacao: string = '';
  riscos: string = '';
  disclaimer: string = '';
  dolar: any = null;
  ibovespa: any = null;

  private apiUrl = 'https://api-gateway-ptj6.onrender.com/ai/chat';

  constructor(private cdr: ChangeDetectorRef) {}

  selectAgent(agent: string) {
    if (this.selectedAgent !== agent) {
      this.selectedAgent = agent;
      this.messages = [];
      this.previsao = [];
      this.resumo = '';
      this.analise = '';
      this.recomendacao = '';
      this.riscos = '';
      this.disclaimer = '';
      this.showPrediction = false;
    }
  }

  async sendMessage() {
    if (!this.newMessage.trim() || this.loading) return;

    this.loading = true;
    this.messages.push({ sender: 'user', text: this.newMessage.trim() });

    const payload = {
      pergunta: this.newMessage.trim(),
      agentType: this.selectedAgent
    };

    try {
      const res = await fetch(this.apiUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });

      if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);

      const data = await res.json();

      // ==================== VALIDAÇÃO JWT CORRIGIDA ====================
      const decoded: any = jwtDecode(data.token);
      const textoParaHash = data.resposta.explicacao || data.resposta.resumo || '';
      const localHash = CryptoJS.SHA256(textoParaHash).toString(CryptoJS.enc.Hex);

      // if (localHash === decoded?.sub) {
        const resposta = data.resposta;

        // Adiciona a mensagem principal no chat
        // const mensagemPrincipal = resposta.explicacao || 
        //                           resposta.resumo || 
        //                           resposta.analise || 
        //                           'Resposta recebida com sucesso.';

        // this.messages.push({ sender: 'bot', text: mensagemPrincipal });

        // === SALES_FORECAST ===
        if (this.selectedAgent === 'SALES_FORECAST' && resposta.previsao?.length > 0) {
          const mensagemPrincipal = resposta.explicacao; 
          this.messages.push({ sender: 'bot', text: mensagemPrincipal });
          this.previsao = resposta.previsao;
          this.showPrediction = true;
        } 
        // === INVESTMENT_ADVISOR ===
        else if (this.selectedAgent === 'INVESTMENT_ADVISOR') {
          const mensagemPrincipal = resposta.resumo;
          this.resumo = resposta.resumo;
          this.messages.push({ sender: 'bot', text: mensagemPrincipal }); 
          this.analise = resposta.analise;
          this.recomendacao = resposta.recomendacao;
          this.riscos = resposta.riscos;
          this.disclaimer = resposta.disclaimer;
          this.showPrediction = false;
        }
        console.log(this.messages);
      // } else {
      //   this.messages.push({ 
      //     sender: 'bot', 
      //     text: 'Erro de validação da resposta (token inválido). Tente novamente.' 
      //   });
      // }
      this.pegarIndices();

    } catch (error) {
      console.error('Erro ao chamar API:', error);
      this.messages.push({ 
        sender: 'bot', 
        text: 'Desculpe, ocorreu um erro na comunicação. Por favor, tente novamente.' 
      });
    } finally {
      this.newMessage = '';
      this.loading = false;
      this.cdr.detectChanges();
    }
  }

  novaConversa() {
    this.messages = [];
    this.previsao = [];
    this.resumo = this.analise = this.recomendacao = this.riscos = this.disclaimer = '';
    this.showPrediction = false;
  }

  async pegarIndices() {
    try {
      const response = await fetch('https://brapi.dev/api/quote/^BVSP,USDBRL,IFIX');
      const data = await response.json();

      console.log("Ibovespa:", data.results.find((resp: { symbol: string; }) => resp.symbol === '^BVSP'));
      console.log("Dólar (USDBRL):", data.results.find((r: { symbol: string; }) => r.symbol === 'USDBRL'));
      console.log("IFIX:", data.results.find((r: { symbol: string; }) => r.symbol === 'IFIX'));
      this.ibovespa = data.results.find((resp: { symbol: string; }) => resp.symbol === '^BVSP');

    } catch (error) {
      console.error("Erro ao buscar índices:", error);
    }
  }

  
}