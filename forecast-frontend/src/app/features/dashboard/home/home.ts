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

async ngOnInit() {
  const token = localStorage.getItem('token');
  this.isLoggedIn = !!token;

  if (token) {
    try {
      const res = await fetch('https://api-gateway-ptj6.onrender.com/auth/home', {
        headers: { Authorization: `Bearer ${token}` }
      });
      if (res.ok) {
        const data = await res.json();
        this.username = data.username || data.email;
        this.isAdmin = data.isAdmin ? true : false;
      }
    } catch (e) {
      console.log("Token inválido → logout");
      localStorage.removeItem('token');
      this.isLoggedIn = false;
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
