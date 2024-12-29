export interface Chambre {
  id?: number;
  name: string;
  description: string;
  type: string;
  available: boolean;
  price: string;
  foyerId?: number;
  foyer?: { id: number } | null;
  foyerNom?: string;  //
}
