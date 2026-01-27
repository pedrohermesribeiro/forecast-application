import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from '../users/users-routing-module';
import { RoleForm } from '../roles/role-form/role-form';
import { LoginComponent } from './login/login.component';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    UsersRoutingModule,
    RoleForm,
    LoginComponent
  ]
})
export class AuthModule { }