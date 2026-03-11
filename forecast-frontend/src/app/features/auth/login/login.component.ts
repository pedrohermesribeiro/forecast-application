import { Component } from '@angular/core';
import { Role, User } from '../../../service/auth-service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { RoleData } from '../model/roleData';
import * as CryptoJS from 'crypto-js';
import { jwtDecode } from 'jwt-decode';
import { environment } from '../../../../environments/environment.prod';
//import { RoleData } from '../model/roleData';

interface LoginRequest {
  email: string;
  password: string;
}

interface LoginResponse {
  token: string;
}


let token = localStorage.getItem('token'); // Armazenar token JWT

role: RoleData;
const roleUser = {id: null, name: 'USER'}


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';       // ← mudei de username para email (consistente com backend)
  password: string = '';
  
  loading = false;
  errorMessage = '';
  respLogin: any = '';

  //private apiUrl = 'http://localhost:8080/auth/login';  // ajuste se gateway ou proxy
  private apiBase = 'https://api-gateway-ptj6.onrender.com/auth/login';

  //private usersUrl = `${this.apiBase}/users`;
  //private apiUrl = `${this.apiBase}/auth/login`;
  //private apiUrl = `${environment.apiUrl}/auth/login`;  // ou /auth/login
    private apiUrl = 'https://api-gateway-ptj6.onrender.com/auth/login';
  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  generateHash(value: string): string {
    return CryptoJS.SHA256(value).toString(CryptoJS.enc.Hex);
  }

  cadastrar(){
    this.router.navigate(['/users/new'], {
    state: {role: roleUser},  // ← passa como query param (ex.: /users/new?role=admin)
  });

  }

  async onLogin() {
    this.loading = true;
    this.errorMessage = '';

    const loginData: LoginRequest = {
      email: this.username,
      password: this.password
    };

    try {
      const response = await fetch(this.apiUrl, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(loginData)
    }).then(async response => {
      this.respLogin = await response.json();
      const decoded = jwtDecode(this.respLogin.token);
      //const email = this.respLogin.loginRequest.email;
      //const password = this.respLogin.loginRequest.password;
      const localHash = this.generateHash(this.username.trim() + this.password.trim());
      const localHashPassword = this.generateHash(this.password.trim());
      console.log("decoded: ", decoded,"localHash: ",localHash,"username:",this.username);
      if (response.ok) {
          //token = result;
          //localStorage.setItem('token', this.respLogin.token); // Armazene o token
          if(decoded.sub === localHash){
            this.router.navigate(['/chat']);
          }else {
            this.router.navigate(['/login']);
          }
          
        } else {
          console.warn("Falha ao obter dados do usuário logado.");
        }
    })
    } catch (error) {
        console.error('Erro ao logar:', error);
        alert('Falha ao logar. Tente novamente.');
    }


    /*
    this.http.post<LoginResponse>(this.apiUrl, loginData).subscribe({
      next: (response) => {
        console.log('Login bem-sucedido:', response);
        //localStorage.setItem('token', response.token);
        this.router.navigate(['/chat']);  // ou sua rota protegida
      },
      error: (err) => {
          console.error('Erro no login:', err);
          this.errorMessage = err.status === 401 
            ? 'Email ou senha inválidos.' 
            : 'Erro no servidor.';
        }
      });*/
  
  }
  


}