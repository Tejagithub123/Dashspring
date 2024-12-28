export interface Personnel {
  id?: number;
  Description: string;
  type: string;
  availble: boolean;
  price: string;
  foyer?: { id: number } | null;// Add cin field
}
