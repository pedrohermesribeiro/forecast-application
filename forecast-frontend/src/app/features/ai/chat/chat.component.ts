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
  selectedAgent: string =  "";  // Agente padrão
  selectedAgent02: string = 'INVESTMENT_ADVISOR';
  messages: ChatMessage[] = [];
  newMessage: string = '';
  previsao: any[] = [];
  resumo: any[] = [];
  analise: any[] = [];
  recomendacao: any[] = [];
  riscos: any[] = [];
  disclaimer: any[] = [];
  showPrediction: boolean = false;
  loading: boolean = false;

  private apiUrl = 'https://api-gateway-ptj6.onrender.com/ai/chat'; // ajuste se necessário

  constructor(private cdr: ChangeDetectorRef) {}

  selectAgent(agent: string) {
    this.selectedAgent = agent == 'SALES_FORECAST' ? 'SALES_FORECAST' : 'INVESTMENT_ADVISOR'; 
    if (this.selectedAgent === 'SALES_FORECAST') {
      //this.selectedAgent = agent;
      this.messages = [];
      this.previsao = [];
      this.showPrediction = true;
    }
    if (this.selectedAgent === 'INVESTMENT_ADVISOR') {
      //this.selectedAgent02 = agent;
      this.resumo = [];
      this.analise = [];
      this.recomendacao = [];
      this.riscos = [];
      this.disclaimer = [];
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

      if (!res.ok) {
        throw new Error(`HTTP error! status: ${res.status}`);
      }

      const data = await res.json();

      // Validação do token JWT (manter segurança)
      const decoded: any = jwtDecode(data.token);
      const localHash = CryptoJS.SHA256(data.resposta.explicacao || '').toString(CryptoJS.enc.Hex);

      if (localHash === decoded?.sub) {
        const resposta = data.resposta;

        this.messages.push({
          sender: 'bot',
          text: resposta.explicacao || resposta.resumo || resposta.analise || resposta.recomendacao || resposta.risco || resposta.disclaimer || 'Resposta recebida com sucesso.'
        });

        // Mostra gráfico de previsão apenas no agente de vendas
        if (this.selectedAgent === 'SALES_FORECAST' && resposta.previsao && resposta.previsao.length > 0) {
          this.previsao = resposta.previsao;
          this.showPrediction = true;
        }
        if (this.selectedAgent === 'INVESTMENT_ADVISOR' && resposta.resumo && resposta.recomendacao && resposta.risco && resposta.analise && resposta.disclaimer) {
          this.resumo = resposta.resumo;
          this.analise = resposta.analise;
          this.recomendacao = resposta.recomendacao;
          this.riscos = resposta.riscos;
          this.disclaimer = resposta.disclaimer;
          this.showPrediction = false;
        }
      } else {
        this.messages.push({ sender: 'bot', text: 'Erro de validação da resposta. Tente novamente.' });
      }

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
    this.showPrediction = false;
  }
}