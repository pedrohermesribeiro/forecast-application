import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

interface LoginResponse {
  token: string;
}

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],   // ✅ OBRIGATÓRIO para ngModel
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username: string = '';
  password: string = '';

  loading = false;
  errorMessage = '';

  private apiUrl = 'http://localhost:8082/auth/login';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  onLogin() {

    this.loading = true;
    this.errorMessage = '';

    const loginData = {
      username: this.username,
      password: this.password
    };

    this.http.post<LoginResponse>(this.apiUrl, loginData).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        this.router.navigate(['/chat']);
      },
      error: (err) => {
        console.error('Erro no login:', err);
        this.errorMessage = 'Usuário ou senha inválidos.';
        this.loading = false;
      },
      complete: () => this.loading = false
    });
  }
}
