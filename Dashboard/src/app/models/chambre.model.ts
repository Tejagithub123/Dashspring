export interface Chambre {
  id?: number;
  name: string;
  description: string;
  type: string;
  availble: boolean;
  price: string;
  foyerId?: number;
  foyer?: { id: number } | null;
  foyerNom?: string;  //
}
