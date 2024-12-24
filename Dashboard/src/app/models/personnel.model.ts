export interface Personnel {
  id?: number;
  nom: string;
  prenom: string;
  email: string;
  dateNaissance: string;
  mdp: string;
  foyerId?: number;
  cin: number;  // Add cin field
}
