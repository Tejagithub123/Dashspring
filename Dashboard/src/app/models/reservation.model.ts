export interface Reservation {
    id?: number;
    chambre: { id: number };
    etudiant: { id: number };
    reservationDate: string;
  }
  