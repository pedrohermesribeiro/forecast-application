import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User, UserService } from '../../../service/user.service';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

const roleUser = {id: null, name: 'ADMIN'}

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
    private http: HttpClient,
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

  novoUsuario(): void {
    this.router.navigate(['/users/new'], {
    state: {role: roleUser},  // ← passa como query param (ex.: /users/new?role=admin)

  });

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

