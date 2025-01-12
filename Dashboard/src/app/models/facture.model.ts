export interface Facture {
    id: number;
    prix: number;
    dateFacture: Date;
    status: 'PAYEE' | 'IMPAYEE';
    etudiant: {
      id: number;
      
    };
    reservation: {
      id: number;
      
    };
  }