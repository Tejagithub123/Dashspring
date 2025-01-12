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
  private apiUrl = 'http://localhost:8090/etudiant/facture'; 
  private apiUrl1 = 'http://localhost:8090/personnel/facture';
  constructor(
    private http: HttpClient,
    private userStorageService: UserStorageService ,
    private authService: AuthService
  ) {}

 
  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Get the token from storage
    return new HttpHeaders({
      Authorization: `Bearer ${token}`, // Add the Bearer token to the headers
    });
  }

 
  getFacturesByEtudiant(etudiantId: number): Observable<Facture[]> {
    const headers = this.getHeaders(); // Include the Bearer token in the request
    return this.http.get<Facture[]>(`${this.apiUrl}/${etudiantId}`, { headers });
  }


  markAsPaid(factureId: number): Observable<Facture> {
    const headers = this.getHeaders(); // Include the Bearer token in the request
    return this.http.put<Facture>(
      `${this.apiUrl}/payer/${factureId}`,
      {}, // Empty body
      { headers }
    );
  } 

   getFacturesByFoyerId(foyerId: number): Observable<Facture[]> {
    const headers = this.getHeaders(); // Include the Bearer token in the request
    return this.http.get<Facture[]>(`${this.apiUrl1}/foyer/${foyerId}`, { headers });
  }
   // Get all invoices
   getAllFactures(): Observable<Facture[]> {
    const headers = this.getHeaders(); // Include the Bearer token in the request
    return this.http.get<Facture[]>(`${this.apiUrl1}/all`, { headers });
  }
}