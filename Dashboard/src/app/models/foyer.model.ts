export class Foyer {
  id?: number;
  nom: string;
  personnel?: any; // Optional personnel object (if a personnel is linked)
  latitude: number;
  longitude: number;

  constructor(id: number, nom: string, latitude: number, longitude: number, personnel?: any) {
    this.id = id;
    this.nom = nom;
    this.latitude = latitude;
    this.longitude = longitude;
    this.personnel = personnel;
  }
}
