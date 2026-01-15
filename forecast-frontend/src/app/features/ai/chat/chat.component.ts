import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs';
//import { environment } from '../environments/environment';
import { environment } from '../../../../environments/environment';






interface ChatMessage {
  sender: 'user' | 'bot';
  text: string;
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
  // ✅ URL do API Gateway no Azure
  //private apiUrl = `${environment.apiUrl}/chat`;

  
 //private apiUrl = environment.apiUrl;

  //private apiUrl = `/ai/chat`;

private apiUrl = 'http://localhost:8080/ai/chat';
  //private apiUrl = 'http://localhost:8081/ai/chat';

  message: { sender: string, text: string }[] = [];
  messag: { sender: string, text: string }[] = [];
  explicacao: string = '';
  constructor(private http: HttpClient) {}


 /* this.http.post(`${environment.apiUrl}/ai/chat`, body)
  .subscribe(...);*/

  /*oldendMessage(event: Event) {
    event.preventDefault();

    if (!this.newMessage.trim()) return;

    const userMessage: ChatMessage = {
      sender: 'user',
      text: this.newMessage,
      timestamp: new Date().toLocaleTimeString()
    };
    this.messages.push(userMessage);

    const payload = { pergunta: this.newMessage };
    const messageToSend = this.newMessage;
    this.newMessage = '';

    // ✅ Chama API do backend
    this.http.post<any>(this.apiUrl, payload).subscribe({
      next: (response) => {
        console.log("response: ",response.text);
        let resposta = response.text;

        // Se vier lista de previsão, concatena no texto
        if (response.previsao) {
          const previsaoTexto = resposta
            .map((p: any) => `${p.mes}: ${p.vendas.toLocaleString('pt-BR')}`)
            .join('\n');
          resposta += `\n\n📊 Previsão:\n${previsaoTexto}`;
        }

        const botMessage: ChatMessage = {
          sender: 'bot',
          text: resposta || 'Sem resposta no momento.',
          timestamp: new Date().toLocaleTimeString()
        };
        this.messages.push(botMessage);
      },
      error: (err) => {
        const errorMessage: ChatMessage = {
          sender: 'bot',
          text: '⚠️ Erro ao se conectar com o servidor.',
          timestamp: new Date().toLocaleTimeString()
        };
        this.messages.push(errorMessage);
        console.error('Erro na API:', err);
      }
    });
  }*/

    novaPergunta(){
      this.message = [];
      this.newMessage = '';
      this.explicacao = '';
    }
 parsed: string = '';


  async sendMessage(event?: Event) {

      try {
        this.message.push({ sender: 'user', text: this.newMessage });
        const respo = await fetch(this.apiUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ "pergunta": this.newMessage})
        }).then(async respo => {
          //console.log("Parsed:", await respo.json());
          if (respo === null) throw new Error("Erro ao criar jogo");
          const explicacao = await respo.json();
          this.message.push({ sender: 'bot', text: explicacao.explicacao });
          
          //return respo.json();
        })
        /*.then(data =>{
          //this.message.push({ sender: 'bot', text: data.explicacao });
          console.log("Data: ", data.explicacao)
        })*/
      } catch (error) {
        console.error('Erro ao cadastrar:', error);
        alert('Falha ao cadastrar. Tente novamente.');
    }
  }  


/*async sendMessage() {
 console.log("environment: ", this.apiUrl);
  try {
    // Adiciona mensagem do usuário
    this.message.push({ sender: 'user', text: this.newMessage });

    // Faz POST para o backend
    const response = await fetch(this.apiUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ pergunta: this.newMessage })
    });

    if (!response.ok) {
      throw new Error("Erro ao chamar API");
    }

    // Lê o JSON de resposta
    const data = await response.json();
    console.log("Data recebida:", data);

    // Adiciona resposta do bot
    this.message.push({ sender: 'bot', text: data.explicacao || 'Sem resposta' });

  } catch (error) {
    console.error('Erro ao cadastrar:', error);
    this.message.push({ sender: 'bot', text: "Erro na comunicação com o servidor" });
  }*/

  // Limpa input
  //this.newMessage = '';

  /*
const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
//return this.httpClient.post<T>(this.httpUtilService.prepareUrlForRequest(url), body, {headers: headers})

this.http.post<any>(this.apiUrl, { pergunta: this.newMessage }, {headers: headers})
  .subscribe({
    next: (resp) => {
      this.message.push({ sender: 'bot', text: resp.explicacao || 'Sem resposta' });
    },
    error: (err) => {
      this.message.push({ sender: 'bot', text: err.error.errorMessage });
    }
  });*/


 /* if (!this.newMessage.trim()) return;

  this.http.post<any>(this.apiUrl, { pergunta: this.newMessage }, { headers })
    .subscribe({
      next: (resp) => {
        this.message.push({ sender: 'bot', text: resp.explicacao || 'Sem resposta' });
      },
      error: (err) => {
        this.message.push({ sender: 'bot', text: err.error?.errorMessage || 'Erro no servidor' });
      }
    });*/




}











  



/*
                        if (!respo.ok) {
                    const errorText = await respo;
                    throw new Error("erro");
                }
                //console.log("Retorno do movimento Realizado antes : ", updateGame);
                const Data = await respo.json();
                console.log("Data: ", Data)
                this.message.push({ sender: 'bot', text: Data.explicacao });
        //next: (respo: any) => {
        const resp = respo;
        console.log("response: ",respo);
        //this.explicacao = resp;  
        let resposta = respo;
        this.newResp  = JSON.stringify({ resp })
        console.log("Respo:", respo);
        console.log("Resp:", resp);
        console.log("newResp:", this.newResp);

          console.log("newResp ",this.newResp);
        //}*/










 /* this.http.post<any>('http://localhost:8081/ai/chat', { "pergunta": "Qual a previsão de vendas o mercado de cerveja para o próximo mês?" })
  .subscribe({
    next: (response: any) => {
        console.log("response: ",response);

        let resposta = response.text;

        //if (response.previsao) {
          const previsaoTexto = resposta
            .map((p: any) => `${p.mes}: ${p.vendas.toLocaleString('pt-BR')}`)
            .join('\n');
          resposta += `\n\n📊 Previsão:\n${previsaoTexto}`;
        //}

        
          const botMessage: ChatMessage = {
          sender: 'bot',
          text: resposta || 'Sem resposta no momento.',
          timestamp: new Date().toLocaleTimeString()
        };
        this.messages.push(botMessage);
    } }); 
  //.subscribe(response => console.log(response), error => console.error(error));
  //}
    //event.preventDefault();

    /*if (!this.newMessage.trim()) return;

    const userMessage: ChatMessage = {
      sender: 'user',
      text: this.newMessage,
      timestamp: new Date().toLocaleTimeString()
    };
    this.messages.push(userMessage);

    const payload = { pergunta: this.newMessage };
    const messageToSend = this.newMessage;
    this.newMessage = '';

    // ✅ Chama API do backend
    this.http.post<any>(this.apiUrl, payload).subscribe({
      next: (response) => {
        console.log("response: ",response.text);
        let resposta = response.text;

        // Se vier lista de previsão, concatena no texto
        if (response.previsao) {
          const previsaoTexto = resposta
            .map((p: any) => `${p.mes}: ${p.vendas.toLocaleString('pt-BR')}`)
            .join('\n');
          resposta += `\n\n📊 Previsão:\n${previsaoTexto}`;
        }

        const botMessage: ChatMessage = {
          sender: 'bot',
          text: resposta || 'Sem resposta no momento.',
          timestamp: new Date().toLocaleTimeString()
        };
        this.messages.push(botMessage);
      },
      error: (err) => {
        const errorMessage: ChatMessage = {
          sender: 'bot',
          text: '⚠️ Erro ao se conectar com o servidor.',
          timestamp: new Date().toLocaleTimeString()
        };
        this.messages.push(errorMessage);
        console.error('Erro na API:', err);
      }
    });*/
    
   /* async chat(){
    async chat(){
    if (!this.newMessage.trim()) return;

    // Adiciona a mensagem do usuário na tela
    this.message.push({ sender: 'user', text: this.newMessage });
    const headers = new HttpHeaders({
  'Content-Type': 'application/json',
  'Accept': 'application/json, text/plain;q=0.9'
});
    // Chama a API do chatbot
    this.http.post<any>(
  'http://localhost:8081/ai/chat',
  { message: this.newMessage },
  { headers }
).pipe(
    map((raw: string) => {
      // Se já for JSON, parse; se vier vazio ou inválido, cai no catchError
      this.parsed = JSON.parse(raw);
      return this.parsed;
    }),
    catchError((err) => {
      // Se o backend já retornou JSON correto (application/json),
      // podemos tentar usar err.error (às vezes vem como objeto)
      if (err?.error && typeof err.error === 'object') return(err.error);
      throw err; // propaga para o subscribe.error
    })
  )
      .subscribe({
        next: (resp: any) => {
          // Adiciona resposta do bot
          const explicacao = this.explicacao ?? 'Sem explicação';
          this.message.push({ sender: 'bot', text: explicacao || 'Sem resposta' });
        },
        error: (err) => {
          //this.message.push({ sender: 'bot', text: err });
          this.message.push({ sender: 'bot', text: "erro" });
        }
      });

    // Limpa o input
    this.newMessage = '';
  }
  }*/
  














