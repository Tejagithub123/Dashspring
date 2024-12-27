import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ToggleService } from '../toggle.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  @Output() menuState = new EventEmitter();

  opened: boolean = true;
  showMenu = false; // False by default since hidden
  col = 12;

  // Dropdown visibility state
  isDropdownVisible: boolean = false;

  data = {
    toggle: this.showMenu,
    col: this.col
  };

  constructor(private toggleService: ToggleService, private router: Router) {}

  ngOnInit(): void {}

  toggleSideBar() {
    console.log("Inside toggleMenu");
    this.data.toggle = !this.data.toggle;
    this.data.col = this.data.toggle ? 12 : 9;
    this.menuState.emit(this.data);
    this.toggleService.showSidebar = false;
  }

  // Toggle dropdown visibility
  toggleDropdown() {
    this.isDropdownVisible = !this.isDropdownVisible;
  }

  // Logout functionality
  logout() {
    localStorage.removeItem('token'); // Remove token from local storage
    this.router.navigate(['/login']); // Redirect to login page
  }
}
