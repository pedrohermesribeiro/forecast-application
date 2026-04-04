// import { ChangeDetectorRef, Component } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { CommonModule } from '@angular/common';
// import { FormsModule } from '@angular/forms';
// import { HttpHeaders } from '@angular/common/http';
// import { catchError, map } from 'rxjs';
// //import { environment } from '../environments/environment';
// import { environment } from '../../../../environments/environment.prod';
// import { Router } from '@angular/router';
// import { jwtDecode } from 'jwt-decode';
// import * as CryptoJS from 'crypto-js';
// //import { PredictionComponent } from '../prediction/prediction.component';
// import { PredictionComponent } from '../prediction/prediction.component';

// interface ChatMessage {
//   sender: 'user' | 'bot';
//   text: any;
//   timestamp: string;
// }
// let tokens = localStorage.getItem('token'); // Armazenar token JWT
// @Component({
//   selector: 'app-chat',
//   standalone: true,
//   imports: [CommonModule, FormsModule, PredictionComponent],
//   templateUrl: './chat.component.html',
//   styleUrls: ['./chat.component.css']
// })
// export class ChatComponent {

//   messages: ChatMessage[] = [];
//   newMessage: string = '';
//   newResp: any = '';
//   respChat: any;

//   loading = false;
//   errorMessage = '';

//   previsao: { mes: string; vendas: number; taxa: number }[] = [];
//   showPrediction: boolean = false;

// //private apiUrl = 'http://localhost:8080/ai/chat';

// private apiBase = environment.apiUrl;

// private apiUrl = 'https://api-gateway-ptj6.onrender.com/ai/chat';


// //private apiUrl = `${environment.apiUrl}/ai/chat`;  // ou /auth/login

//   message: { sender: string, text: string }[] = [];
//   messag: { sender: string, text: string }[] = [];
//   explicacao: string = '';
//   //cdr: any;
//   constructor(
//     private http: HttpClient,
//     private router: Router,
//     private cdr: ChangeDetectorRef  // ← injete aqui!
//   ) {}

// generateHash(value: string): string {
//   return CryptoJS.SHA256(value).toString(CryptoJS.enc.Hex);
// }



//     novaPergunta(){
//       this.message = [];
//       this.newMessage = '';
//       this.explicacao = '';
//       this.showPrediction = false;
//     }
//  parsed: string = '';


// async sendMessage() {
//     this.loading = true;
//     this.errorMessage = '';
//     // try {
//     //     //this.message.push({ sender: 'user', text: this.newMessage });
//     //     const respo = await fetch(this.apiUrl, {
//     //         method: 'POST',
//     //         headers: {
//     //         'Content-Type': 'application/json'
//     //     },
//     //         body: JSON.stringify({ "pergunta": this.newMessage})
//     //     }).then(async respo => {


//     try {
//       const respo = await fetch(this.apiUrl, {
//           method: 'POST',
//           headers: { 'Content-Type': 'application/json' },
//           body: JSON.stringify({ "pergunta": this.newMessage})
//       });
//           if (!respo.ok) {
//             // Aqui você pode tentar ler o body de erro se existir
//             let errorBody = '';
//             try {
//               errorBody = await respo.text();  // use .text() primeiro, mais seguro
//             } catch {}
            
//             console.error(`Erro HTTP ${respo.status}: ${errorBody}`);
//             return;
//           }          
//           //if (respo === null) throw new Error("Erro ao criar jogo");
//           this.respChat = await respo.json();
//           const decoded = jwtDecode(this.respChat.token);
//           const localHash = this.generateHash(this.respChat.resposta.explicacao);
//           console.log('respChat.resposta: ',this.respChat.resposta, 'decoded:',decoded);
//           if(localHash === decoded?.sub){
//              this.explicacao = this.respChat.resposta.explicacao;
//              this.previsao = this.respChat.resposta.previsao || []; // array com {mes, vendas}
//             console.log('explicação: ',this.explicacao,'respChat: ',this.respChat);
//             this.message.push({ sender: 'bot', text: this.explicacao });
//             console.log("Previsão: ",this.previsao);
//             this.showPrediction = this.previsao.length === 6; // só mostra se vier exatamente 3 meses
//             console.log('showPrediction setado para:', this.showPrediction);

//             // Força change detection (opcional, mas ajuda em casos raros)
//             this.cdr.detectChanges(); // injete ChangeDetectorRef no construtor se não tiver
//           }

        
//         } catch (error) {
//           console.error('Erro ao cadastrar:', error);
//           alert('Falha ao cadastrar. Tente novamente.');
//     }
//   }
      
// }


import { Component, ChangeDetectorRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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
  selectedAgent: string = 'SALES_FORECAST';   // Default
  messages: ChatMessage[] = [];
  newMessage: string = '';
  previsao: any[] = [];
  showPrediction: boolean = false;
  loading: boolean = false;

  private apiUrl = 'https://api-gateway-ptj6.onrender.com/ai/chat'; // ajuste se necessário

  constructor(
    private http: HttpClient,
    private cdr: ChangeDetectorRef
  ) {}

  selectAgent(agent: string) {
    if (this.selectedAgent !== agent) {
      this.selectedAgent = agent;
      this.messages = [];           // limpa histórico ao trocar agente
      this.previsao = [];
      this.showPrediction = false;
    }
  }

  async sendMessage() {
    if (!this.newMessage.trim() || this.loading) return;

    this.loading = true;
    this.messages.push({ sender: 'user', text: this.newMessage });

    const payload = {
      pergunta: this.newMessage,
      agentType: this.selectedAgent
    };

    try {
      const res = await fetch(this.apiUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });

      if (!res.ok) throw new Error('Erro na API');

      const data = await res.json();
      
      // Validação do token (manter como você já faz)
      const decoded = jwtDecode(data.token);
      const localHash = CryptoJS.SHA256(data.resposta.explicacao).toString(CryptoJS.enc.Hex);

      if (localHash === decoded?.sub) {
        const resposta = data.resposta;

        this.messages.push({ 
          sender: 'bot', 
          text: resposta.explicacao || resposta.resumo || 'Resposta recebida.' 
        });

        // Só mostra previsão se for o agente de vendas
        if (this.selectedAgent === 'SALES_FORECAST' && resposta.previsao) {
          this.previsao = resposta.previsao;
          this.showPrediction = true;
        }
      }

    } catch (error) {
      console.error(error);
      this.messages.push({ sender: 'bot', text: 'Desculpe, ocorreu um erro. Tente novamente.' });
    } finally {
      this.newMessage = '';
      this.loading = false;
      this.cdr.detectChanges();
    }
  }
}





