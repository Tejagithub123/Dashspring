import { Component, OnInit } from '@angular/core';
import { Reservation } from 'src/app/models/reservation.model';
import { ReservationService } from 'src/app/services/reservation/reservation.service';
import { ChambreService } from 'src/app/services/chambre/chambre.service';
import { Chambre } from 'src/app/models/chambre.model';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  
  reservation: Reservation = {
    chambre: { id: 0 },
    etudiant: { id: 0 },  
    reservationDate: '',
  };
  
  chambres: Chambre[] = [];
  selectedChambreId: number | null = null;

  constructor(
    private reservationService: ReservationService,
    private chambreService: ChambreService
  ) {}

  ngOnInit(): void {
    this.loadEtudiantIdFromToken();
    this.loadChambres();
  }

  
  loadEtudiantIdFromToken(): void {
    const token = localStorage.getItem('token');
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      this.reservation.etudiant.id = payload.id;  
    }
  }

  
  loadChambres(): void {
    this.chambreService.getListeChambres().subscribe(
      (data: Chambre[]) => {
        this.chambres = data;
        console.log('Chambres:', this.chambres);
      },
      error => {
        console.error('Erreur lors de la récupération des chambres:', error);
      }
    );
  }

  
  onChambreSelect(event: Event): void {
    const target = event.target as HTMLSelectElement;  
    const chambreId = Number(target.value);  
    this.selectedChambreId = chambreId;

    
    this.reservation.chambre.id = chambreId;
  }

  
  createReservation(): void {
    if (this.selectedChambreId === null) {
      console.error('Please select a chambre.');
      return;
    }

    
    const reservationPayload: Reservation = {
      chambre: { id: this.reservation.chambre.id },
      etudiant: { id: this.reservation.etudiant.id },
      reservationDate: this.reservation.reservationDate
    };

    
    console.log('Sending reservation data to API:', JSON.stringify(reservationPayload, null, 2));

    
    this.reservationService.addReservation(reservationPayload).subscribe(
      response => {
        console.log('Reservation created successfully', response);
        alert('Reservation created successfully.');
      },
      error => {
        console.error('Error creating reservation', error);
        alert('You have already reserved this chambre.');
      }
    );
  }
}
