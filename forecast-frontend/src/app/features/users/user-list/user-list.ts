import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User, UserService } from '../../../service/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-list.html',
  styleUrls: ['./user-list.css']
})
export class UserList implements OnInit {

  users: User[] = [];

  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarUsuarios();
  }

  carregarUsuarios(): void {
    this.userService.getAll().subscribe(data => {
      this.users = data;
    });
  }

  // Novo método para formatar as roles
  // getRoleNames(user: User): string {
  //   if (!user.role || user.role.length === 0) {
  //     return 'Nenhuma';
  //   }
    
  //   return user.role
      // .map(rol => rol.name.replace(/^/, ''))  // remove o prefixo ROLE_ se quiser
      // .join(', ');
 // }

  novoUsuario(): void {
    this.router.navigate(['/users/new']);
  }

  editarUsuario(id: number | undefined): void {
    if (id) this.router.navigate([`/users/edit/${id}`]);
  }

  deletarUsuario(id: number | undefined): void {
    if (!id) return;

    if (confirm("Confirma excluir o usuário?")) {
      this.userService.delete(id).subscribe(() => {
        this.carregarUsuarios();
      });
    }
  }
}

