import { Injectable } from '@angular/core';
import { MenuItem } from './Menuitem';

@Injectable({
  providedIn: 'root',
})
export class SidebarService {
  toggled = false;
  private _hasBackgroundImage = true;

  menus: MenuItem[] = [
    {
      title: 'general',
      type: 'header',
    },
    {
      title: 'Foyer',
      icon: 'fa fa-home',  // Replace with appropriate icon
      route: '/foyer',
      type: 'simple',
    },
    {
      title: 'Personnel',
      icon: 'fa fa-users',  // Replace with appropriate icon
      route: '/personnel',
      type: 'simple', // This is a link item
    },
    {
      title: 'Dashboard',
      icon: 'fa fa-tachometer-alt',
      active: false,
      type: 'dropdown',
      badge: {
        text: 'New',
        class: 'badge-warning',
      },
      submenus: [
        {
          title: 'Dashboard 1',
          type: 'simple',
          route: '/dashboard1',  // Ensure each submenu has a route if needed
        },
        {
          title: 'Dashboard 2',
          type: 'simple',
          route: '/dashboard2',  // Ensure each submenu has a route if needed
        },
      ],
    },
    {
      title: 'E-commerce',
      icon: 'fa fa-shopping-cart',
      active: false,
      type: 'dropdown',
      badge: {
        text: '3',
        class: 'badge-danger',
      },
      submenus: [
        {
          title: 'Products',
          type: 'simple',
          route: '/products',
        },
        {
          title: 'Orders',
          type: 'simple',
          route: '/orders',
        },
        {
          title: 'Credit card',
          type: 'simple',
          route: '/credit-card',
        },
      ],
    },
    {
      title: 'Components',
      icon: 'far fa-gem',
      active: false,
      type: 'dropdown',
      submenus: [
        {
          title: 'General',
          type: 'simple',
          route: '/general',
        },
        {
          title: 'Forms',
          type: 'simple',
          route: '/forms',
        },
      ],
    },
    {
      title: 'Charts',
      icon: 'fa fa-chart-line',
      active: false,
      type: 'dropdown',
      submenus: [
        {
          title: 'Pie chart',
          type: 'simple',
          route: '/pie-chart',
        },
        {
          title: 'Line chart',
          type: 'simple',
          route: '/line-chart',
        },
      ],
    },
    {
      title: 'Maps',
      icon: 'fa fa-globe',
      active: false,
      type: 'dropdown',
      submenus: [
        {
          title: 'Google maps',
          type: 'simple',
          route: '/google-maps',
        },
        {
          title: 'Open street map',
          type: 'simple',
          route: '/open-street-map',
        },
      ],
    },
    {
      title: 'Extra',
      type: 'header',
    },
    {
      title: 'Documentation',
      icon: 'fa fa-book',
      active: false,
      type: 'simple',
      badge: {
        text: 'Beta',
        class: 'badge-primary',
      },
      route: '/documentation',
    },
    {
      title: 'Calendar',
      icon: 'fa fa-calendar',
      active: false,
      type: 'simple',
      route: '/calendar',
    },
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