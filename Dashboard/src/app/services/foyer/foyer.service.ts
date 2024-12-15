import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Foyer } from '../../models/foyer.model';
import { AuthService } from '../auth/auth.service'; // Import AuthService

@Injectable({
  providedIn: 'root',
})
export class FoyerService {
  private baseUrl = 'http://localhost:8090/admin/foyers'; // Backend API URL

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Get token from localStorage
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  }

  getAllFoyers(): Observable<Foyer[]> {
    return this.http.get<Foyer[]>(this.baseUrl, { headers: this.getHeaders() });
  }

  getFoyerById(id: number): Observable<Foyer> {
    return this.http.get<Foyer>(`${this.baseUrl}/${id}`, { headers: this.getHeaders() });
  }

  addFoyer(foyer: Foyer): Observable<Foyer> {
    return this.http.post<Foyer>(this.baseUrl, foyer, { headers: this.getHeaders() });
  }

  updateFoyer(id: number, foyer: Foyer): Observable<Foyer> {
    return this.http.put<Foyer>(`${this.baseUrl}/${id}`, foyer, { headers: this.getHeaders() });
  }

  deleteFoyer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { headers: this.getHeaders() });
  }
}
