import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent implements OnInit {

  isLoggedIn = false;
  username: string | null = null;
  isAdmin = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    this.isLoggedIn = !!token;

    if (token) {
      try {
        const decoded: any = jwtDecode(token);
        this.username = decoded.email || 'Usuário';
        this.isAdmin = decoded.roles?.some((r: any) => 
          r.name === 'ROLE_ADMIN' || r.includes('ADMIN')
        ) || false;
      } catch (e) {
        localStorage.removeItem('token');
      }
    }
  }

  irParaLogin() { this.router.navigate(['/login']); }
  cadastrar() {
    this.router.navigate(['/users/new'], { state: { role: { name: 'USER' } } });
  }

  irParaChat()    { this.isLoggedIn ? this.router.navigate(['/chat']) : this.irParaLogin(); }
  irParaUsuarios(){ this.isLoggedIn ? this.router.navigate(['/users']) : this.irParaLogin(); }
  irParaRoles()   { this.isLoggedIn && this.isAdmin ? this.router.navigate(['/roles']) : this.irParaLogin(); }

  logout() {
    localStorage.removeItem('token');
    this.isLoggedIn = false;
    this.username = null;
    this.router.navigate(['/home']);
  }
}
