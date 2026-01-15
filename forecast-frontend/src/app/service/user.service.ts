import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Role {
  id?: number;
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

  //private apiUrl = 'http://localhost:8080/users';

  private usersUrl = 'http://localhost:8080/users';
  private rolesUrl = 'http://localhost:8080/roles'; // endpoint das roles

  constructor(private http: HttpClient) {}

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  getById(id: number): Observable<User> {
    return this.http.get<User>(`${this.usersUrl}/${id}`);
  }

  create(user: User): Observable<User> {
    console.log(user);
    return this.http.post<User>(this.usersUrl, user);
  }

  update(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.usersUrl}/${id}`, user);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.usersUrl}/${id}`);
  }

  // Métodos de Role
  getAllRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(`${this.rolesUrl}`);
  }

  createRole(role: Role): Observable<Role> {
    return this.http.post<Role>(`${this.rolesUrl}`, role);
  }

  updateRole(id: number, role: Role): Observable<User> {
    return this.http.put<User>(`${this.rolesUrl}/${id}`, role);
  }

  deleteRole(id: number): Observable<any> {
    return this.http.delete(`${this.rolesUrl}/${id}`);
  }
}
