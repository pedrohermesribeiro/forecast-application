import { ChangeDetectorRef, Component, HostListener } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs';
//import { environment } from '../environments/environment';
import { environment } from '../../../../environments/environment.prod';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import * as CryptoJS from 'crypto-js';
//import { PredictionComponent } from '../prediction/prediction.component';
import { PredictionComponent } from '../prediction/prediction.component';

interface ChatMessage {
  sender: 'user' | 'bot';
  text: any;
  timestamp: string;
}
let tokens = localStorage.getItem('token'); // Armazenar token JWT
@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule, PredictionComponent],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent {

  sidebarOpen = false;
  isMobile = false;

  messages: ChatMessage[] = [];
  newMessage: string = '';
  newResp: any = '';
  respChat: any;

  previsao: { mes: string; vendas: number }[] = [];
  showPrediction: boolean = false;

//private apiUrl = 'http://localhost:8080/ai/chat';

private apiBase = environment.apiUrl;

private apiUrl = 'https://api-gateway-ptj6.onrender.com/ai/chat';


//private apiUrl = `${environment.apiUrl}/ai/chat`;  // ou /auth/login

  message: { sender: string, text: string }[] = [];
  messag: { sender: string, text: string }[] = [];
  explicacao: string = '';
  //cdr: any;
  constructor(
    private http: HttpClient,
    private router: Router,
    private cdr: ChangeDetectorRef  // ← injete aqui!
  ) {}

  ngOnInit() {
    this.checkMobile();
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.checkMobile();
  }

  checkMobile() {
    this.isMobile = window.innerWidth <= 768;
    if (!this.isMobile) {
      this.sidebarOpen = true;  // Abre automaticamente no desktop
    }
  }

  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
  }

  // ... seu sendMessage(), novaPergunta(), etc.


generateHash(value: string): string {
  return CryptoJS.SHA256(value).toString(CryptoJS.enc.Hex);
}



    novaPergunta(){
      this.message = [];
      this.newMessage = '';
      this.explicacao = '';
      this.showPrediction = false;
    }
 parsed: string = '';


async sendMessage() {
    try {
        //this.message.push({ sender: 'user', text: this.newMessage });
        const respo = await fetch(this.apiUrl, {
            method: 'POST',
            headers: {
            'Content-Type': 'application/json'
        },
            body: JSON.stringify({ "pergunta": this.newMessage})
        }).then(async respo => {
          if (respo === null) throw new Error("Erro ao criar jogo");
          this.respChat = await respo.json();
          const decoded = jwtDecode(this.respChat.token);
          const localHash = this.generateHash(this.respChat.resposta.explicacao);
          console.log('respChat.resposta: ',this.respChat.resposta, 'decoded:',decoded);
          if(localHash === decoded?.sub){
             this.explicacao = this.respChat.resposta.explicacao;
             this.previsao = this.respChat.resposta.previsao || []; // array com {mes, vendas}
            console.log('explicação: ',this.explicacao,'respChat: ',this.respChat);
            this.message.push({ sender: 'bot', text: this.explicacao });
            console.log("Previsão: ",this.previsao);
            this.showPrediction = this.previsao.length === 6; // só mostra se vier exatamente 3 meses
            console.log('showPrediction setado para:', this.showPrediction);

            // Força change detection (opcional, mas ajuda em casos raros)
            this.cdr.detectChanges(); // injete ChangeDetectorRef no construtor se não tiver
          }

        })
        } catch (error) {
          console.error('Erro ao cadastrar:', error);
          alert('Falha ao cadastrar. Tente novamente.');
    }
  }
      
}





