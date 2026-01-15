import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { ChatComponent } from './features/ai/chat/chat.component';
import { UserList } from './features/users/user-list/user-list';
import { UserForm } from './features/users/user-form/user-form';
import { RoleForm } from './features/roles/role-form/role-form';
import { RoleList } from './features/roles/role-list/role-list';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'chat', component: ChatComponent },
  //{ path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'users', component: UserList },
  { path: 'users/new', component: UserForm },
  { path: 'users/edit/:id', component: UserForm },
  { path: 'users/:id', component: UserForm },
  { path: 'roles', component: RoleList},
  { path: 'roles/new', component: RoleForm},
  { path: 'roles/:id', component: RoleForm},
  { path: 'roles/edit/:id', component: RoleForm},

];
