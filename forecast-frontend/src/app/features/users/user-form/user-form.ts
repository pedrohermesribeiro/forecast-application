import { UserService, User, Role } from './../../../service/user.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-form.html',
  styleUrls: ['./user-form.css']
})
export class UserForm implements OnInit {

  user: User = {
    username: '', email: '', roles: [],
  };

  roleAux: String = '';

  roleAu: Role[] = []

  selectedRoleId: number | null = null; // para o select

  editing = false;
  userId?: number;
  availableRoles: Role[] = [];
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
  this.userService.getAllRoles().subscribe(roles => {
      this.availableRoles = roles;
      console.log("Relação de roles: ", roles);
    });

    this.userId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.userId) {
      this.editing = true;
      this.userService.getById(this.userId).subscribe(u => {
        this.user = u;
      });
    }
  }

  // isRoleSelected(role: Role): boolean {
  //   return this.user.roles.some(r => r.id === role.id || r.name === role.name);
  // }

  // toggleRole(role: Role, checked: boolean): void {
  //   if (checked) {
  //     if (!this.user.roles.some(r => r.id === role.id)) {
  //       this.user.roles.push(role);
  //     }
  //   } else {
  //     this.user.roles = this.user.roles.filter(r => r.id !== role.id);
  //   }
  // }
  // Quando o usuário seleciona uma role no <select>
  addRole() {
    // if (!this.selectedRoleId) return;
    console.log("Objeto role selecionado id: ", this.selectedRoleId);
    const role = this.availableRoles.find(r => Number(r.id) === Number(this.selectedRoleId));
    console.log("Objeto role selecionado: ", role);
    if (!role) {
    console.error("Role não encontrada no array availableRoles");
    return;
  }
// Verifica se já existe no array (usando .roles!)
  const alreadyExists = this.user.roles.some(existing => Number(existing.id) === Number(role.id));

  if (!alreadyExists) {
    this.user.roles.push(role);  // ← aqui também .roles
    console.log("Role adicionada com sucesso. Roles atuais:", this.user.roles);
  } else {
    console.log("Role já existe no usuário");
  }
    console.log("Objeto role selecionado: ", this.user.roles);
    // Limpa a seleção após adicionar (opcional)
    this.selectedRoleId = null;
  }

salvar(): void {
    // const role = this.availableRoles.find(r => r.name === this.roleAux);
    // this.user.role.push(role)
    this.addRole();
    const request = this.editing && this.userId
      ? this.userService.update(this.userId, this.user)
      : this.userService.create(this.user);

    request.subscribe(() => {
      this.router.navigate(['/users']);
    });
  }

  voltar(): void {
    this.router.navigate(['/users']);
  }
}

