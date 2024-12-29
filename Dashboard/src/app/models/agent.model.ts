export interface Agent {
    id?: number; // Optional, as it will be generated on the backend
    nom: string;
    prenom: string;
    specialite: string;
    email: string;
    mdp:string;
    cin?: number; // Make this optional
    dateNaissance: string;
  }
  