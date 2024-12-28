import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';  // Import the AuthService
import { Router } from '@angular/router'; // Import the Router
import { UserStorageService } from 'src/app/storage/user-storage.service';
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


  constructor(private authService: AuthService, private router: Router) { } 

  ngOnInit(): void {
  }

  // Handle form submission
  onSubmit(): void {
    if (this.loginData.email && this.loginData.password) {
      this.authService.login(this.loginData).subscribe(
        (response) => {
          UserStorageService.saveToken(response.token)
          console.log('Login successful', response);
          this.router.navigate(['']);
        },
        (error) => {
          console.error('Login failed', error);
        }
      );
    }
  }
}
