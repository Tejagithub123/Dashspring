import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  subMenuState = { toggle: false }; // Example state management
  isLoginRoute = false;
  isResetPasswordRoute = false;
  constructor(private router: Router) {}

  ngOnInit(): void {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // Check if the current route is '/login'
        this.isLoginRoute = event.url === '/login' || event.urlAfterRedirects === '/login';
        this.isResetPasswordRoute = event.url === '/reset-password' || event.urlAfterRedirects === '/reset-password';
      }
    });
  }

  burgerClicked(state: any): void {
    // Handle menu toggle
    this.subMenuState.toggle = state.toggle;
  }
}
