import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Reservation } from 'src/app/models/reservation.model';
import { ChambreService } from 'src/app/services/chambre/chambre.service';
import { ReservationService } from 'src/app/services/reservation/reservation.service';
import { UserStorageService } from 'src/app/storage/user-storage.service';

@Component({
  selector: 'app-chambre-list',
  templateUrl: './reservation-list-component.component.html',
  styleUrls: ['./reservation-list-component.component.scss'],
})
export class ReservationListComponent implements OnInit {
  reservations: Reservation[] = [];
  
 
  role: string = UserStorageService.getUserRole();
  
  constructor(
    private chambreService: ChambreService,
    private reservationService: ReservationService,
    private modalService: NgbModal, 
   
  ) {}

  ngOnInit(): void {
    this.loadChambres();
    this.role = UserStorageService.getUserRole(); // Get role on initialization
  }

  loadChambres(): void {
    const userRole = UserStorageService.getUserRole(); // Get the current user's role
    const userId = Number(UserStorageService.getUserId()); // Get the current user's ID
    const foyer_id= Number(UserStorageService.getFoyer_Id());
    console.log(foyer_id)
  
    if (userRole === 'ROLE_PERSONNEL') {
      // Call the method to get all reservations for "USER_PERSONNELLE"
      this.chambreService.getReservationsByFoyer(foyer_id).subscribe((reservations) => {
        this.reservations = reservations;
        console.log(reservations);
      });
    } else {
      // Call the method to get reservations by ID for other roles
      this.chambreService.getReservationsByEtudiantId(userId).subscribe((reservations) => {
        this.reservations = reservations;
        console.log(reservations);
      });
    }
  }
    // Approve a reservation
    approveReservation(reservationId: number | undefined): void {
      if (reservationId === undefined) {
        console.error('Reservation ID is undefined');
        window.location.reload();
        return;
      }
      this.reservationService.approveReservation(reservationId).subscribe({
        next: () => {
          alert('Approved successfully!');
         

        },
        error: (err) => {
          console.error('Error approving reservation:', err);
          alert('Failed to approve reservation.');
        },
      });

    }
    rejectReservation(reservationId: number | undefined): void {
      if (reservationId === undefined) {
        console.error('Reservation ID is undefined');
        return;
      }
      this.reservationService.rejectReservation(reservationId).subscribe({
        next: () => {
          alert('Rejected successfully!');
          window.location.reload();
        },
        error: (err) => {
          console.error('Error arejecting reservation:', err);
          alert('Failed to reject reservation.');
        },
      });
    }
    

      }


