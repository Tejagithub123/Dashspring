import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactComponent } from './contact/contact.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { HomeComponent } from './home/home.component';
import { UserListComponent } from './user-list/user-list.component';
import { FoyerComponent } from './components/foyer/foyer.component';
import { PersonnelComponent } from './components/personnel/personnel.component';
const routes: Routes = [{
  path: '',
  component: HomeComponent,
},
{
  path: 'contact',
  component: ContactComponent,
},
{
  path: 'users', 
  component: UserListComponent
},
{
  path:'editsave/:id',
  component: EditUserComponent
} ,
{ path: 'foyer',
 component: FoyerComponent },

{ path: 'personnel', 
component: PersonnelComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
