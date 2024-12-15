import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';  // Import the AuthService

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginData = {
    email: '',
    password: ''
  };

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  // Handle form submission
  onSubmit(): void {
    if (this.loginData.email && this.loginData.password) {
      this.authService.login(this.loginData).subscribe(
        (response) => {
          console.log('Login successful', response);
          // Redirect user or store token as needed
        },
        (error) => {
          console.error('Login failed', error);
        }
      );
    }
  }
}
