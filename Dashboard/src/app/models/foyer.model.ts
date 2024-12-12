export class Foyer {
    id?: number;
    nom: string;
    personnel?: any; // Optional personnel object (if a personnel is linked)
  
    constructor(id: number, nom: string, personnel?: any) {
      this.id = id;
      this.nom = nom;
      this.personnel = personnel;
    }
  }
  