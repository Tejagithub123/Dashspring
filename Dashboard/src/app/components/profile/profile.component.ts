// profile.component.ts
import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';  // Import the user service

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: any;
  agentId: number | undefined;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      this.agentId = payload.id;
      this.loadUserProfile();
    }
  }

  loadUserProfile(): void {
    if (this.agentId) {
      this.userService.getUserById(this.agentId).subscribe(
        (data) => {
          this.user = data;
          console.log(data)  // Populate user data
        },
        (error) => {
          console.error('Error fetching user profile:', error);
        }
      );
    }
  }
}
