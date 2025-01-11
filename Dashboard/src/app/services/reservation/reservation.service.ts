import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { Reservation } from 'src/app/models/reservation.model';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  private apiUrl = 'http://localhost:8090/etudiant/reservation'; // Your backend URL

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Assuming your AuthService has a method to get the token
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  }

  // Function to create a reservation
  addReservation(reservation: Reservation): Observable<Reservation> {
    const headers = this.getHeaders();
    return this.http.post<Reservation>(this.apiUrl, reservation, { headers });
  }

  // Function to get reservations by Etudiant ID
  getReservationsByEtudiantId(etudiantId: number): Observable<Reservation[]> {
    const headers = this.getHeaders();
    return this.http.get<Reservation[]>(
      `${this.apiUrl}/etudiant/${etudiantId}`,
      { headers }
    );
  }


  approveReservation(reservationId: number): Observable<any> {
    const headers = this.getHeaders();
    const updatedReservation = {
      confirmed: 'VALID' ,
      availble: false 

    };
    return this.http.put<any>(
      `http://localhost:8090/personnel/reservation/${reservationId}`, 
      updatedReservation, 
      { headers }
    );
  }

  rejectReservation(reservationId: number): Observable<any> {
    const headers = this.getHeaders();
    const updatedReservation = {
      confirmed: 'REJECTED'  
    };
    return this.http.put<any>(
      `http://localhost:8090/personnel/reservation/${reservationId}`, 
      updatedReservation, 
      { headers }
    );
    
  }
}
