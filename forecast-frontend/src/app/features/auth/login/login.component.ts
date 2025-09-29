import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { NgModel } from '@angular/forms';
import { LoginData } from '../model/loginData';
import { CommonModule } from '@angular/common';

interface LoginResponse {
  token: string;
}

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {


  loading = false;
  errorMessage = '';
  

  // URL do endpoint de login no API Gateway (ajuste conforme necessário)
  private apiUrl = 'https://forecast-app-dev-bjhxh3fjceeud7cv.brazilsouth-01.azurewebsites.net/api/auth/login';

  constructor(private http: HttpClient, private router: Router) {}

  onLogin() {
    this.loading = true;
    this.errorMessage = '';

    this.http.post<LoginResponse>(this.apiUrl, this.loginData).subscribe({
      next: (response) => {
        // Salva token no localStorage
        localStorage.setItem('token', response.token);

        // Redireciona para o chat
        this.router.navigate(['/chat']);
      },
      error: (err) => {
        console.error('Erro no login:', err);
        this.errorMessage = 'Usuário ou senha inválidos.';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
  loginData<T>(apiUrl: string, loginData: any) {
    throw new Error('Method not implemented.');
  }
}

