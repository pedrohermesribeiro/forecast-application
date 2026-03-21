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
  this.loading = true;
  const token = localStorage.getItem('token');

  if (!token) {
    this.isLoggedIn = false;
    this.finalizarCarregamento();
    return;
  }

  try {
    const res = await fetch('https://api-gateway-ptj6.onrender.com/auth/home', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Accept': 'application/json'
      }
    });

    // dentro do try, depois de pegar o token:
    try {
      const decoded: any = jwtDecode(token);
      console.log('Token decodificado (debug):', decoded);
      // sub pode ser o hash ou email, dependendo do que você colocou no backend
    } catch (e) {
      console.warn('Token não decodificável');
    }

    if (!res.ok) {
      if (res.status === 401) {
        throw new Error('Sessão expirada ou token inválido');
      }
      throw new Error(`Erro ${res.status}`);
    }

    const data: { success: boolean; email: string; username: string; admin: boolean; message: string } = await res.json();

    if (!data.success) {
      throw new Error(data.message || 'Resposta inválida do servidor');
    }

    this.username   = data.username || data.email || null;
    this.isAdmin    = !!data.admin;
    this.isLoggedIn = true;

    this.finalizarCarregamento();

  } catch (err: any) {
    console.error('Falha ao carregar informações do usuário:', err);
    localStorage.removeItem('token');
    this.isLoggedIn = false;
    this.finalizarCarregamento(err.message || 'Erro ao carregar dados. Tente novamente.');
  }
}

  // buscarUsuario(user:string ): void {
  //   this.loading = true;
  //   //this.error = null;
  //   //this.users = null;

  //   this.userService.findByEmail(user).subscribe({
  //     next: (user) => {
  //       this.users = user;
  //       this.isAdmin = this.ehAdminPelaPrimeiraRole(user.roles);
  //       this.finalizarCarregamento();
  //       console.log('Usuário encontrado:', user);
  //     },
  //     error: (err) => {
  //       this.error = 'Erro ao buscar usuário: ' + (err.message || err.statusText);
  //       this.loading = false;
  //       console.error(err);
  //     }
  //   });
  // }

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
