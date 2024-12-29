import { Injectable } from '@angular/core';
import { MenuItem } from './MenuItem';

@Injectable({
  providedIn: 'root',
})
export class SidebarService {
  toggled = false;
  private _hasBackgroundImage = true;

  menus: MenuItem[] = [
            //ADMIN//
    {
      title: 'Menu',
      type: 'header',
    },
    {
      title: 'Foyer',
      icon: 'fa fa-home',  
      route: '/foyer',
      type: 'simple',
      condition: ['ROLE_ADMIN'],
    },
    {
      title: 'Add personnel',
      icon: 'fa fa-users',  
      route: '/personnel',
      type: 'simple', 
      condition: ['ROLE_ADMIN'],
    },
    {
      title: 'list personnels',
      icon: 'fa fa-list',
      route : '/liste-personnels',
      type: 'simple', 
      condition: ['ROLE_ADMIN'],
    },
                   //PERSONNEL//
    {
      title: 'Add chambre',
      icon: 'fa fa-users',  
      route: '/chambre',
      type: 'simple', 
      condition: ['ROLE_PERSONNEL'],
    },
    {
      title: 'list chambre',
      icon: 'fa fa-list',
      route : '/liste-chambres',
      type: 'simple', 
      condition: ['ROLE_PERSONNEL','ROLE_ETUDIANT'],
    },
    {
      title: 'Add etudiant',
      icon: 'fa fa-users',  
      route: '/etudiant',
      type: 'simple', 
      condition: ['ROLE_PERSONNEL','ROLE_ADMIN'],
    },
    {
      title: 'Add etudiant',
      icon: 'fa fa-users',  
      route: '/liste-etudiants',
      type: 'simple', 
      condition: ['ROLE_ADMIN'],
    },
    // {
    //   title: 'E-commerce',
    //   icon: 'fa fa-shopping-cart',
    //   active: false,
    //   type: 'dropdown',
    //   condition: 'ROLE_ADMIN',
    
    //   submenus: [
    //     {
    //       title: 'Products',
    //       type: 'simple',
    //       route: '/products',
    //     },
    //     {
    //       title: 'Orders',
    //       type: 'simple',
    //       route: '/orders',
    //     },
    //     {
    //       title: 'Credit card',
    //       type: 'simple',
    //       route: '/credit-card',
    //     },
    //   ],
    // },
    // {
    //   title: 'Components',
    //   icon: 'far fa-gem',
    //   active: false,
    //   type: 'dropdown',
    //   condition: 'ROLE_PERSONNEL',
    //   submenus: [
    //     {
    //       title: 'General',
    //       type: 'simple',
    //       route: '/general',
    //     },
    //     {
    //       title: 'Forms',
    //       type: 'simple',
    //       route: '/forms',
    //     },
    //   ],
    // },
    // {
    //   title: 'Charts',
    //   icon: 'fa fa-address-book',
    //   active: false,
    //   type: 'dropdown',
    //   condition: 'ROLE_PERSONNEL',
    //   submenus: [
    //     {
    //       title: 'Pie chart',
    //       type: 'simple',
    //       route: '/pie-chart',
    //     },
    //     {
    //       title: 'Line chart',
    //       type: 'simple',
    //       route: '/line-chart',
    //     },
    //   ],
    // },
    // {
    //   title: 'Maps',
    //   icon: 'fa fa-globe',
    //   active: false,
    //   type: 'dropdown',
    //   condition: 'ROLE_PERSONNEL',
    //   submenus: [
    //     {
    //       title: 'Google maps',
    //       type: 'simple',
    //       route: '/google-maps',
    //     },
    //     {
    //       title: 'Open street map',
    //       type: 'simple',
    //       route: '/open-street-map',
    //     },
    //   ],
    // },
    // {
    //   title: 'Extra',
    //   type: 'header',
    // },
    // {
    //   title: 'Documentation',
    //   icon: 'fa fa-book',
    //   active: false,
    //   type: 'simple',
    //   badge: {
    //     text: 'Beta',
    //     class: 'badge-primary',
    //   },
    //   route: '/documentation',
    // },
    // {
    //   title: 'Calendar',
    //   icon: 'fa fa-calendar',
    //   active: false,
    //   type: 'simple',
    //   route: '/calendar',
    // },
  ];

  constructor() {}

  toggle() {
    this.toggled = !this.toggled;
  }

  getSidebarState() {
    return this.toggled;
  }

  setSidebarState(state: boolean) {
    this.toggled = state;
  }

  getMenuList(): MenuItem[] {
    return this.menus;
  }

  get hasBackgroundImage(): boolean {
    return this._hasBackgroundImage;
  }

  set hasBackgroundImage(hasBackgroundImage: boolean) {
    this._hasBackgroundImage = hasBackgroundImage;
  }
}