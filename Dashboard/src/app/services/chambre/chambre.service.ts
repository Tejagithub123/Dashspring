import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Chambre } from 'src/app/models/chambre.model';
import { UserStorageService } from 'src/app/storage/user-storage.service'; // Make sure to import the user storage service
import { Reservation } from 'src/app/models/reservation.model';

@Injectable({
  providedIn: 'root', // Ensures the service is provided at the root level
})
export class ChambreService {
  private baseUrl: string ="";

  constructor(private http: HttpClient) {
    // Set the base URL based on the user's role
    const role = UserStorageService.getUserRole();
    if (role === 'ROLE_ETUDIANT') {
      this.baseUrl = 'http://localhost:8090/etudiant';
    } else if (role === 'ROLE_PERSONNEL')  {
      this.baseUrl = 'http://localhost:8090/personnel';
    } 
  }

  // Helper method to get headers with Bearer Token
  private getHeaders(): HttpHeaders {
    // Retrieve the token from localStorage (or other storage)
    const token = localStorage.getItem('token');
    
    // If no token is found, we send the request without the Authorization header
    const headers = token ? new HttpHeaders().set('Authorization', `Bearer ${token}`).set( 'Content-Type', 'application/json') : new HttpHeaders();
    
    return headers;
  }

  // Get all chambres
  getAll(id: number): Observable<Chambre[]> {
    return this.http.get<Chambre[]>(`${this.baseUrl}/Allchambres/${id}`, {
      headers: this.getHeaders(),
    });
  }

  getAllChambre(): Observable<Chambre[]> {
    return this.http.get<Chambre[]>(`${this.baseUrl}/Allchambres`, {
      headers: this.getHeaders(),
    });
  }

  // Get a chambre by ID
  getById(id: number): Observable<Chambre> {
    return this.http.get<Chambre>(`${this.baseUrl}/Onechambre/${id}`, {
      headers: this.getHeaders(),
    });
  }

  // Create a new chambre (no foyer assigned)
  create(chambre: Chambre, foyerId: number): Observable<Chambre> {
    return this.http.post<Chambre>(`${this.baseUrl}/chambre?foyerId=${foyerId}`, chambre, {
      headers: this.getHeaders(),
    });
  }

  // Update a chambre
  update(id: number, chambre: Partial<Chambre>): Observable<Chambre> {
    return this.http.patch<Chambre>(`${this.baseUrl}/chambres/${id}`, chambre, {
      headers: this.getHeaders(),
    });
  }

  // Delete a chambre
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/deleteChambre/${id}`, {
      headers: this.getHeaders(),
    });
  }
  // Add résérvation
   
  AddReservation(id_user: number, id_chambre: number): Observable<Reservation> {
    const url = `${this.baseUrl}/reservation?chambreId=${id_chambre}&etudiantId=${id_user}`;
    const headers = this.getHeaders();
  
    return this.http.post<Reservation>(url, null, { headers });
  }

  getReservationsByEtudiantId(etudiantId: number): Observable<Reservation[]> {
    const url = `${this.baseUrl}/reservation/${etudiantId}`;
    const headers = this.getHeaders();

    return this.http.get<Reservation[]>(url,{ headers });
  }
  getReservationsByFoyer(foyerId: number): Observable<Reservation[]> {
    const url = `http://localhost:8090/personnel/reservationByFoyer/${foyerId}`;
    const headers = this.getHeaders();

    return this.http.get<Reservation[]>(url,{ headers });
  }


  // Assign a foyer to a chambre
  assignFoyer(chambreId: number, foyerId: number): Observable<Chambre> {
    const params = new HttpParams().set('foyerId', foyerId.toString());
    return this.http.put<Chambre>(
      `${this.baseUrl}/${chambreId}/foyer`,
      null, // Sending empty body as required by the backend
      { params: params, headers: this.getHeaders() }
    );
  } 

  getListeChambres(): Observable<Chambre[]> {
    return this.http.get<Chambre[]>(`${this.baseUrl}/listechambres`, {
      headers: this.getHeaders(),
    });
  }

  
  

}
