import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing-module';
import { RoleForm } from '../roles/role-form/role-form';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    UsersRoutingModule,
    RoleForm
  ]
})
export class UsersModule { }
