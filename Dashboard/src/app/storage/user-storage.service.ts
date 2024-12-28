import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

const TOKEN = 'token';

@Injectable({
    providedIn: 'root',
})
export class UserStorageService {
    constructor() {}

    static saveToken(token: string): void {
        window.localStorage.removeItem(TOKEN);
        window.localStorage.setItem(TOKEN, token);
        this.decodeAndSaveUserDetails(token);  // Decode token and save user details
    }

    static decodeAndSaveUserDetails(token: string): void {
        try {
            const decodedToken: any = jwtDecode(token);  // Decode the token
            const userDetails = {
                id: decodedToken.id,   // Extract id from decoded token
                role: decodedToken.role,  // Extract role from decoded token
            };
            // Save id and role to localStorage
            window.localStorage.setItem('user_id', userDetails.id);
            window.localStorage.setItem('user_role', userDetails.role);
            window.localStorage.setItem('foyer_id', "");
        } catch (error) {
            console.error('Token decoding failed:', error);
        }
    }

    static setFoyer_Id(foyer_id: string) {
        window.localStorage.setItem('foyer_id', foyer_id);
    }
    static getFoyer_Id(): string {
        return localStorage.getItem('foyer_id')||'';
    }
    static getToken(): string {
        return localStorage.getItem(TOKEN)||'';
    }

    static getUserId(): string {
        return localStorage.getItem('user_id') || '';
    }
  
    

    static getUserRole(): string {
        return localStorage.getItem('user_role') || '';
    }

    static isAdminLoggedIn(): boolean {
        const role = this.getUserRole();
        return role === 'ADMIN';
    }

    static isPersonnelLoggedIn(): boolean {
        const role = this.getUserRole();
        return role === 'PERSONNEL';
    }
    static SignOut():void{
        localStorage.removeItem('token');
        window.localStorage.removeItem('user_id');
        window.localStorage.removeItem('user_role');
        window.localStorage.removeItem('foyer_id');
    }

}
