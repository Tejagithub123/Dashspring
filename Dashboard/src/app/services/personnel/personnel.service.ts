import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Personnel } from '../../models/personnel.model';

@Injectable({
  providedIn: 'root',
})
export class PersonnelService {
  private baseUrl = 'http://localhost:8090/admin/personnels';

  constructor(private http: HttpClient) {}

  // Helper method to get headers with Bearer Token
  private getHeaders(): HttpHeaders {
    // Retrieve the token from localStorage (or other storage)
    const token = localStorage.getItem('token');
    
    // If no token is found, we send the request without the Authorization header
    const headers = token ? new HttpHeaders().set('Authorization', `Bearer ${token}`) : new HttpHeaders();
    
    return headers;
  }

  getAll(): Observable<Personnel[]> {
    return this.http.get<Personnel[]>(`${this.baseUrl}/Allpersonnels`, {
      headers: this.getHeaders(),
    });
  }

  getById(id: number): Observable<Personnel> {
    return this.http.get<Personnel>(`${this.baseUrl}/${id}`, {
      headers: this.getHeaders(),
    });
  }

  create(personnel: Personnel, foyerId: number): Observable<Personnel> {
    return this.http.post<Personnel>(`${this.baseUrl}?foyerId=${foyerId}`, personnel, {
      headers: this.getHeaders(),
    });
  }

  update(id: number, personnel: Partial<Personnel>): Observable<Personnel> {
    return this.http.patch<Personnel>(`${this.baseUrl}/${id}`, personnel, {
      headers: this.getHeaders(),
    });
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, {
      headers: this.getHeaders(),
    });
  }

  assignFoyer(personnelId: number, foyerId: number): Observable<Personnel> {
    return this.http.put<Personnel>(
      `${this.baseUrl}/${personnelId}/foyer?foyerId=${foyerId}`,
      {},
      {
        headers: this.getHeaders(),
      }
    );
  }
}
