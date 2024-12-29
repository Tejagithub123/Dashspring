import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Etudiant } from 'src/app/models/etudiant.model';
import { UserStorageService } from 'src/app/storage/user-storage.service';

@Injectable({
  providedIn: 'root',
})
export class EtudiantService {
  private baseUrl: string;

  constructor(private http: HttpClient) {
    // Get the role of the user from UserStorageService
    const role = UserStorageService.getUserRole();
    
    // Set baseUrl based on the role
    if (role === 'ROLE_ADMIN') {
      this.baseUrl = 'http://localhost:8090/admin';
    } else {
      this.baseUrl = 'http://localhost:8090/personnel';
    }
  }

  // Helper method to get headers with Bearer Token
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    const headers = token ? new HttpHeaders().set('Authorization', `Bearer ${token}`) : new HttpHeaders();
    return headers;
  }

  // Get all etudiants
  getAll(): Observable<Etudiant[]> {
    return this.http.get<Etudiant[]>(`${this.baseUrl}/etudinat/Alletudiants`, {
      headers: this.getHeaders(),
    });
  }

  // Get an etudiant by ID
  getById(id: number): Observable<Etudiant> {
    return this.http.get<Etudiant>(`${this.baseUrl}/Oneetudiant/${id}`, {
      headers: this.getHeaders(),
    });
  }

  // Create a new etudiant (no foyer assigned)
  create(etudiant: Etudiant): Observable<Etudiant> {
    return this.http.post<Etudiant>(`${this.baseUrl}/etudiant`, etudiant, {
      headers: this.getHeaders(),
    });
  }

  // Delete an etudiant
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/etudiants/${id}`, {
      headers: this.getHeaders(),
    });
  }
}
