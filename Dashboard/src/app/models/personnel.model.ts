export interface Personnel {
  id?: number;
  nom: string;
  prenom: string;
  email: string;
  dateNaissance: string;
  mdp: string;
  foyerId?: number;
  cin: number; 
  foyerName?: string;  // Add cin field
}
