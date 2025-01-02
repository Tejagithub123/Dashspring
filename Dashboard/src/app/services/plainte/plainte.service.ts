import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Plainte } from '../../models/plainte.model';

@Injectable({
  providedIn: 'root'
})
export class PlainteService {

  private apiUrl = 'http://localhost:8090/personnel/plainte'; // URL de base

  constructor(private http: HttpClient) { }

  // Méthode pour récupérer toutes les plaintes
  getAllPlaintes(): Observable<Plainte[]> {
    return this.http.get<Plainte[]>(`${this.apiUrl}/Allplaintes`, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.getToken()}`
      })
    });
  }

 


    // Méthode pour récupérer toutes les plaintes
    getAllPlaintesAgent(): Observable<Plainte[]> {
      return this.http.get<Plainte[]>(` http://localhost:8090/agent/plainte/Allplaintes`, {
        headers: new HttpHeaders({
          'Authorization': `Bearer ${this.getToken()}`
        })
      });
    }


  // Méthode pour récupérer une plainte spécifique par ID
  getPlainteById(id: number): Observable<Plainte> {
    return this.http.get<Plainte>(`${this.apiUrl}/${id}`, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.getToken()}`
      })
    });
  }

  // Méthode pour ajouter une plainte
  createPlainte(plainte: Plainte): Observable<Plainte> {
    return this.http.post<Plainte>(this.apiUrl, plainte, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.getToken()}`
      })
    });
  }

  // Méthode pour mettre à jour une plainte existante
  updatePlainte(id: number, plainte: Plainte): Observable<Plainte> {
    return this.http.put<Plainte>(`${this.apiUrl}/${id}`, plainte, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.getToken()}`
      })
    });
  }

  // Méthode pour supprimer une plainte
  deletePlainte(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.getToken()}`
      })
    });
  }

  // Récupérer le token de l'utilisateur
  private getToken(): string {
    return localStorage.getItem('token') || ''; // récupère le token de localStorage
  } 
  
  cloturerPlainte(id: number): Observable<Plainte> {
    const token = localStorage.getItem('token'); // Assume token is stored in localStorage
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.patch<Plainte>(`http://localhost:8090/agent/plainte/${id}/cloturer`, {}, { headers });
  }



}
