import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';  // <-- Add this import
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = 'http://localhost:8090/auth/login'; // Your backend API for login
  private resetPasswordUrl = 'http://localhost:8090/auth';
  constructor(private http: HttpClient) {}

  login(loginData: { email: string, password: string }): Observable<any> {
    // Create query parameters from the loginData
    const params = new HttpParams()
      .set('email', loginData.email)
      .set('mdp', loginData.password);

    return this.http.post(this.loginUrl, null, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
      params: params,
      responseType: 'json'
    }).pipe(
      // Save token to localStorage on successful login
      tap((response: any) => {
        if (response.token) {
          localStorage.setItem('token', response.token);  // Save token to localStorage
        }
      })
    );
  }

  // Method to get token from localStorage
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Method to check if the user is logged in
  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  // Method to logout the user
  logout(): void {
    localStorage.removeItem('token');  // Remove token from localStorage
  }



  resetPassword(email: string): Observable<any> {
    return this.http.post(`${this.resetPasswordUrl}/reset-password`, null, {
      params: new HttpParams().set('email', email)
    });
  }
  
}
