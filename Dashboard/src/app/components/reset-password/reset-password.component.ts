import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {
  email: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onResetPassword() {
    if (!this.email) {
      alert('Email is required');
      return;
    }

    this.authService.resetPassword(this.email).subscribe({
      next: () => {
        alert('Password reset email sent');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        alert('Error sending reset email: ' + err.error);
        console.log("err",err.error)
      }
    });
  }
}
