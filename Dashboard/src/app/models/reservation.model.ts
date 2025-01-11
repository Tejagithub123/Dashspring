
export interface Reservation {
  id?: number; // Optional reservation ID
  chambre: {
    id: number; // ID of the chambre
    available?: boolean; // Optional availability status for chambre
  };
  etudiant: {
    id: number; // ID of the etudiant
  };
  reservationDate: string; // Reservation date as a string
  confirmed: string; // Confirmation status
}
