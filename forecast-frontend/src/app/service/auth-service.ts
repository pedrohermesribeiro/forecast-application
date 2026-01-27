import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Role {
  id: number;
  name: string; // ex: "ROLE_ADMIN", "ROLE_USER"
}




export interface User {
  id?: number;
  username: string;
  email: string;
  password?: string;
  roles: Role[];
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl = 'http://localhost:8080/users';
  private rolesUrl = 'http://localhost:8080/roles'; // endpoint das roles

  constructor(private http: HttpClient) {}


}
