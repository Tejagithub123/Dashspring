import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { Reservation } from 'src/app/models/reservation.model';
@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private apiUrl = 'http://localhost:8090/etudiant/reservation'; // Your backend URL

  constructor(private http: HttpClient, private authService: AuthService) {}

  // Function to make a POST request to create a reservation with Bearer Token
  addReservation(reservation: Reservation): Observable<Reservation> {
    const token = this.authService.getToken(); // Assuming your AuthService has a method to get the token

    // Create headers with Bearer token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    // Make the POST request with the Bearer token in the headers
    return this.http.post<Reservation>(this.apiUrl, reservation, { headers });
  } 

  getReservationsByEtudiantId(etudiantId: number): Observable<Reservation[]> {
    const token = this.authService.getToken();
     // Create headers with Bearer token
     const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.get<Reservation[]>(`${this.apiUrl}/etudiant/${etudiantId}`, { headers });
  }
}
