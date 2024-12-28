import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Personnel } from '../../models/personnel.model';

@Injectable({
  providedIn: 'root',
})
export class ChambreService {
  private baseUrl = 'http://localhost:8090/admin';

  constructor(private http: HttpClient) {}

  // Helper method to get headers with Bearer Token
  private getHeaders(): HttpHeaders {
    // Retrieve the token from localStorage (or other storage)
    const token = localStorage.getItem('token');
    
    // If no token is found, we send the request without the Authorization header
    const headers = token ? new HttpHeaders().set('Authorization', `Bearer ${token}`) : new HttpHeaders();
    
    return headers;
  }

  // Get all personnels
  getAll(): Observable<Personnel[]> {
    return this.http.get<Personnel[]>(`${this.baseUrl}/Allchambres`, {
      headers: this.getHeaders(),
    });
  }

  // Get a personnel by ID
  getById(id: number): Observable<Personnel> {
    return this.http.get<Personnel>(`${this.baseUrl}/Onechambre/${id}`, {
      headers: this.getHeaders(),
    });
  }

  // Create a new personnel (no foyer assigned)
  create(personnel: Personnel): Observable<Personnel> {
    return this.http.post<Personnel>(`${this.baseUrl}/chambre`, personnel, {
      headers: this.getHeaders(),
    });
  }

  // Update a personnel
  update(id: number, personnel: Partial<Personnel>): Observable<Personnel> {
    return this.http.patch<Personnel>(`${this.baseUrl}/chambres/${id}`, personnel, {
      headers: this.getHeaders(),
    });
  }

  // Delete a personnel
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, {
      headers: this.getHeaders(),
    });
  }

  // Assign a foyer to a personnel
  assignFoyer(personnelId: number, foyerId: number): Observable<Personnel> {
    const params = new HttpParams().set('foyerId', foyerId.toString());
    return this.http.put<Personnel>(
      `${this.baseUrl}/${personnelId}/foyer`,
      null, // Sending empty body as required by the backend
      { params: params, headers: this.getHeaders() }
    );
  }
}
