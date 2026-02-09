import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs';
//import { environment } from '../environments/environment';
import { environment } from '../../../../environments/environment';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import * as CryptoJS from 'crypto-js';


interface ChatMessage {
  sender: 'user' | 'bot';
  text: any;
  timestamp: string;
}

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent {

  messages: ChatMessage[] = [];
  newMessage: string = '';
  newResp: any = '';
  respChat: any;

private apiUrl = 'http://localhost:8080/ai/chat';

  message: { sender: string, text: string }[] = [];
  messag: { sender: string, text: string }[] = [];
  explicacao: string = '';
  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

generateHash(value: string): string {
  return CryptoJS.SHA256(value).toString(CryptoJS.enc.Hex);
}



    novaPergunta(){
      this.message = [];
      this.newMessage = '';
      this.explicacao = '';
    }
 parsed: string = '';


async sendMessage() {
    try {
        this.message.push({ sender: 'user', text: this.newMessage });
        const respo = await fetch(this.apiUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ "pergunta": this.newMessage})
        }).then(async respo => {
          if (respo === null) throw new Error("Erro ao criar jogo");
          this.respChat = await respo.json();
          const decoded = jwtDecode(this.respChat.token);
          const localHash = this.generateHash(this.respChat.resposta.explicacao);
          console.log('respChat.resposta: ',this.respChat.resposta, 'decoded:',decoded);
          if(localHash === decoded?.sub){
             this.explicacao = this.respChat.resposta.explicacao;
            console.log('explicação: ',this.explicacao,'respChat: ',this.respChat);
            this.message.push({ sender: 'bot', text: this.explicacao });
          }

        })
        } catch (error) {
          console.error('Erro ao cadastrar:', error);
          alert('Falha ao cadastrar. Tente novamente.');
    }
  }
      
}





