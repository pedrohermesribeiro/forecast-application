import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { ChatComponent } from './features/ai/chat/chat.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'chat', component: ChatComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];
