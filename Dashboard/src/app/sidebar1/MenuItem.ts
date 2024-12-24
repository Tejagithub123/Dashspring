
export interface MenuItem {
  title: string;
  type: 'header' | 'dropdown' | 'simple';
  icon?: string;
  active?: boolean;
  badge?: {
    text: string;
    class: string;
  };
  route?: string; // Optional for routing
  submenus?: MenuItem[];
}