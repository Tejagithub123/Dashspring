import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Facture } from 'src/app/models/facture.model';
import { UserStorageService } from '../../storage/user-storage.service';
import { AuthService } from '../auth/auth.service'; // Import AuthService

@Injectable({
  providedIn: 'root',
})
export class FactureService {
  private apiUrl = 'http://localhost:8090/etudiant/facture'; // Backend API URL

  constructor(
    private http: HttpClient,
    private userStorageService: UserStorageService ,
    private authService: AuthService
  ) {}

  // Helper method to get headers with the Bearer token
  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Get the token from storage
    return new HttpHeaders({
      Authorization: `Bearer ${token}`, // Add the Bearer token to the headers
    });
  }

  // Get invoices by student ID
  getFacturesByEtudiant(etudiantId: number): Observable<Facture[]> {
    const headers = this.getHeaders(); // Include the Bearer token in the request
    return this.http.get<Facture[]>(`${this.apiUrl}/${etudiantId}`, { headers });
  }

  // Mark an invoice as paid
  markAsPaid(factureId: number): Observable<Facture> {
    const headers = this.getHeaders(); // Include the Bearer token in the request
    return this.http.put<Facture>(
      `${this.apiUrl}/payer/${factureId}`,
      {}, // Empty body
      { headers }
    );
  }
}