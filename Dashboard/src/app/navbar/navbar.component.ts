import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ToggleService } from '../toggle.service';
import { UserStorageService } from 'src/app/storage/user-storage.service';
import {PersonnelService} from 'src/app/services/personnel/personnel.service'
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

  constructor(private toggleService: ToggleService, private router: Router,private personnelService: PersonnelService) {}

  ngOnInit(): void {
    // console.log("User role:", UserStorageService.getUserRole());
    // console.log("Foyer ID:", UserStorageService.getFoyer_Id());
  
    if (UserStorageService.getUserRole() == "ROLE_PERSONNEL" && UserStorageService.getFoyer_Id()==""){
      this.personnelService.getFoyerId(Number(UserStorageService.getUserId())).subscribe(
        (response) => {
          UserStorageService.setFoyer_Id(String(response));
          console.log(response)
        },
        (error) => {
          console.error('Login failed', error);
        }
      );
      // String id =UserStorageService.getUserId;///////////////////////////getById
      
      

    }
  }

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
    
    localStorage.removeItem('token');
    window.localStorage.removeItem('user_id');
    window.localStorage.removeItem('user_role');
    window.localStorage.removeItem('foyer_id'); // Remove token from local storage
    this.router.navigate(['/login']); // Redirect to login page
  }

}
