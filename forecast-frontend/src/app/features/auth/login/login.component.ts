import { Component } from '@angular/core';
import { Role, User } from '../../../service/auth-service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { RoleData } from '../model/roleData';
//import { RoleData } from '../model/roleData';

interface LoginRequest {
  email: string;
  password: string;
}

interface LoginResponse {
  token: string;
}

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
  email: string = '';       // ← mudei de username para email (consistente com backend)
  password: string = '';

  loading = false;
  errorMessage = '';

  private apiUrl = 'http://localhost:8080/auth/login';  // ajuste se gateway ou proxy

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  cadastrar(){
    this.router.navigate(['/users/new'], {
    state: {role: roleUser},  // ← passa como query param (ex.: /users/new?role=admin)
  });

  }

  onLogin() {
    this.loading = true;
    this.errorMessage = '';

    const loginData: LoginRequest = {
      email: this.email,
      password: this.password
    };

    this.http.post<LoginResponse>(this.apiUrl, loginData).pipe(
      tap(response => {
        console.log('Login bem-sucedido:', response);
        //localStorage.setItem('token', response.token);
        this.router.navigate(['/chat']);  // ou sua rota protegida
      }),
      catchError(err => {
        console.error('Erro no login:', err);
        this.errorMessage = err.status === 401 
          ? 'Email ou senha inválidos.' 
          : 'Erro no servidor. Tente novamente.';
        return of(null);
      })
    ).subscribe(() => this.loading = false);
  }





}