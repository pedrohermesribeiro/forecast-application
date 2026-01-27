import { UserService, User, Role } from '../../../service/user.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
//import { filter } from 'rxjs';


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

  roleUser = history.state?.role;
  availableRolesUser: Role[] = [];
  roleFromState: any;
  selectedRoleId: number | null = null; // para o select

  editing = false;
  userId?: number;
  availableRoles: Role[] = [];
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {

  }

  ngOnInit(): void {
  this.userService.getAllRoles().subscribe(roles => {
      this.availableRoles = roles;
          this.roleFromState = this.availableRoles.find(function(role) {
          return role?.name === 'USER';
    });
      this.roleUser.id = this.roleFromState.id;
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
    const role = this.availableRoles.find(r => Number(r.id) === Number(this.selectedRoleId));
    if (!role) {
    console.error("Role não encontrada no array availableRoles");
    return;
  }
// Verifica se já existe no array (usando .roles!)
  const alreadyExists = this.user.roles.some(existing => Number(existing.id) === Number(role.id));

  if (!alreadyExists) {
    console.log("Role adicionada com sucesso. Roles atuais:", this.roleUser);

    if(this.roleUser.name === 'USER'){
      this.user.roles.push(this.roleUser);  // ← aqui também .roles
    }else{
      this.user.roles.push(role);  // ← aqui também .roles
    }
    
    console.log("Role adicionada com sucesso. Roles atuais:", this.user.roles);
  } else {
    console.log("Role já existe no usuário");
  }

    this.selectedRoleId = null;
  }

salvar(): void {
    this.addRole();
    const request = this.editing && this.userId
      ? this.userService.update(this.userId, this.user)
      : this.userService.create(this.user);

    request.subscribe(() => {
      if(this.roleUser?.name === 'USER'){
        this.router.navigate(['/login']);
      }else{
        this.router.navigate(['/users']);
      }
      
    });
  }

  voltar(): void {
    this.router.navigate(['/users']);
  }
}

