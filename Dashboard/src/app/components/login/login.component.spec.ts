import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service'; // You'll create this service soon

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginData = { email: '', password: '' };

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.login(this.loginData).subscribe(
      (response) => {
        localStorage.setItem('token', response.token); // Save token in localStorage
        this.router.navigate(['/foyer']); // Redirect to Foyer component
      },
      (error) => {
        console.error('Login failed', error);
        alert('Login failed!');
      }
    );
  }
}
