import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { jwtDecode } from 'jwt-decode';
import { Role, UserService } from '../../../service/user.service';
import { HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})
export class HomeComponent implements OnInit {

  isLoggedIn = false;
  username: string | null = null;
  isAdmin = false;
  loading: boolean = true;
  error: any = null;
  users: any = null;

  constructor(private router: Router, private user: ChangeDetectorRef, private userService: UserService ) {}

async ngOnInit() {
  const token = localStorage.getItem('token');
  this.isLoggedIn = !!token;

  if (token) {
    try {
      const res = await fetch('https://api-gateway-ptj6.onrender.com/auth/home', {
        headers: { Authorization: `Bearer ${token}` }
      });
      console.log('Retorno authorization:', res);
      if (res.ok) {
        const data = await res.json();
        this.username = data.username || data.email;
        //this.isAdmin = this.username === "pedrohermesrib@gmail.com" ? true : false;
        this.buscarUsuario(data.email);
      }
    } catch (e) {
      console.log("Token inválido → logout");
      localStorage.removeItem('token');
      this.isLoggedIn = false;
    }
  }
}

  buscarUsuario(user:string ): void {
    this.loading = true;
    //this.error = null;
    //this.users = null;

    this.userService.findByEmail(user).subscribe({
      next: (user) => {
        this.users = user;
        this.isAdmin = this.ehAdminPelaPrimeiraRole(user.roles);
        this.finalizarCarregamento();
        console.log('Usuário encontrado:', user);
      },
      error: (err) => {
        this.error = 'Erro ao buscar usuário: ' + (err.message || err.statusText);
        this.loading = false;
        console.error(err);
      }
    });
  }

    private ehAdminPelaPrimeiraRole(roles: Role[]): boolean {
    if (!roles || roles.length === 0) {
      return false;
    }

    const primeiraRole = roles[0];
    const nomeRole = primeiraRole.name?.toUpperCase() || '';

    return nomeRole === 'ROLE_ADMIN' || nomeRole === 'ADMIN';
  }

  private finalizarCarregamento(errorMsg?: string): void {
    this.loading = false;
    if (errorMsg) this.error = errorMsg;
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
