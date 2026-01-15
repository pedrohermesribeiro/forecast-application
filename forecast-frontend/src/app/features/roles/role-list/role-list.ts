import { Component, OnInit } from '@angular/core';
import { Role, UserService } from '../../../service/user.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-role-list',
  imports: [CommonModule],
  templateUrl: './role-list.html',
  styleUrl: './role-list.css'
})
export class RoleList {

  roles: Role[] = [];

  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarRoles();
  }

  carregarRoles(): void {
    this.userService.getAllRoles().subscribe(data => {
      this.roles = data;
    });
  }

  novaRole(): void {
    this.router.navigate(['/roles/new']);
  }

  editarRoles(id: number | undefined): void {
    if (id) this.router.navigate([`/roles/edit/${id}`]);
  }

  deletarRoles(id: number | undefined): void {
    if (!id) return;

    if (confirm("Confirma excluir o usuário?")) {
      this.userService.deleteRole(id).subscribe(() => {
        this.carregarRoles();
      });
    }
  }

}
