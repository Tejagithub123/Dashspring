import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Foyer } from '../models/foyer.model';

@Injectable({
  providedIn: 'root',
})
export class FoyerService {
  private baseUrl = 'http://localhost:8090/admin/foyers'; // Backend API URL

  constructor(private http: HttpClient) {}

  getAllFoyers(): Observable<Foyer[]> {
    return this.http.get<Foyer[]>(this.baseUrl);
  }

  getFoyerById(id: number): Observable<Foyer> {
    return this.http.get<Foyer>(`${this.baseUrl}/${id}`);
  }

  addFoyer(foyer: Foyer): Observable<Foyer> {
    return this.http.post<Foyer>(this.baseUrl, foyer);
  }

  updateFoyer(id: number, foyer: Foyer): Observable<Foyer> {
    return this.http.put<Foyer>(`${this.baseUrl}/${id}`, foyer);
  }

  deleteFoyer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
