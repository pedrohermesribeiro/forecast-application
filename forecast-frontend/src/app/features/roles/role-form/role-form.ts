import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Role, UserService } from '../../../service/user.service';

@Component({
  selector: 'app-role-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './role-form.html',
  styleUrl: './role-form.css'
})
export class RoleForm {
role: Role = { name: '' };
  editing = false;
  roleId?: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.roleId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.roleId) {
      this.editing = true;
      // Se quiser editar role existente, implementar getById no backend e aqui
      // Por enquanto, roles geralmente são fixas, então edição pode ser desativada
    }
  }

  salvar(): void {
    const roleToSave = {
      ...this.role,
      name: this.role.name.toUpperCase()
    };

    this.userService.createRole(roleToSave).subscribe(() => {
      this.router.navigate(['/roles']);
    });
  }

  voltar(): void {
    this.router.navigate(['/roles']);
  }
}
