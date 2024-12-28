import { Component, OnInit } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { SidebarService } from './sidebar.service';
import { MenuItem } from './MenuItem'; // Ensure the correct import path
import { UserStorageService } from '../storage/user-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  animations: [
    trigger('slide', [
      state('up', style({ height: 0 })),
      state('down', style({ height: '*' })),
      transition('up <=> down', animate(200)),
    ]),
  ],
})
export class SidebarComponent implements OnInit {
  menus: MenuItem[] = []; // Explicitly type the menus array

  constructor(public sidebarservice: SidebarService,public router: Router) {
    this.menus = sidebarservice.getMenuList();
  }
  UserRole:String = "";
  ngOnInit(): void {
    this.router.events.subscribe(event => {
      
        this.UserRole = UserStorageService.getUserRole();

        // console.log(this.isUserRole)
    });}

  getSideBarState(): boolean {
    return this.sidebarservice.getSidebarState();
  }

  toggle(currentMenu: MenuItem): void { // Explicitly type currentMenu as MenuItem
    if (currentMenu.type === 'dropdown') {
      this.menus.forEach((element) => {
        if (element === currentMenu) {
          currentMenu.active = !currentMenu.active;
        } else {
          element.active = false;
        }
      });
    }
  }

  getState(currentMenu: MenuItem): string { // Explicitly type currentMenu as MenuItem
    return currentMenu.active ? 'down' : 'up';
  }

  hasBackgroundImage(): boolean {
    return this.sidebarservice.hasBackgroundImage;
  }  
  toggleSidebar() {
    this.sidebarservice.toggle();
  }
 

  
}
