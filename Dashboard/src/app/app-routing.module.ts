import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactComponent } from './contact/contact.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { HomeComponent } from './home/home.component';
import { UserListComponent } from './user-list/user-list.component';
import { FoyerComponent } from './components/foyer/foyer.component';
import { PersonnelComponent } from './components/personnel/personnel.component';
import { ChambreComponent } from './components/chambre/chambre.component';
import { LoginComponent } from './components/login/login.component';

import { PersonnelListComponent } from './components/personnel-list-component/personnel-list-component.component';
import { ChambreListComponent } from './components/chambre-list-component/chambre-list-component.component';

import { AgentMaintenanceComponent } from './components/add-agent/add-agent.component';
import { AgentListComponent } from './components/agent-list/agent-list.component';

import { ReservationListComponent } from './components/reservation-list-component/reservation-list-component.component';

import { EtudiantComponent } from './components/etudiant/etudiant.component';
import { EtudiantListComponent } from './components/etudiant-list-component/etudiant-list-component.component';
import { AuthGuard } from './guards/auth.guard';
import { PlaintesComponent } from './components/plaintes/plaintes.component';
import { PlaintesListComponent } from './components/plaintes-list/plaintes-list.component';
import { AgentPlainteComponent } from './components/agent-plainte/agent-plainte.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { ProfileComponent } from './components/profile/profile.component';

import { RoomAvailabilityComponent } from './components/room-availability/room-availability.component';
import { FactureListComponent } from './components/facture-list/facture-list.component';
import { PersonnelFactureListComponent } from './components/personnel-facture-list/personnel-facture-list.component';
const routes: Routes = [  { path: '',  component:HomeComponent , canActivate: [AuthGuard]}, // Redirect to login page by default
{ path: 'login', component: LoginComponent },  // Define the login routev


{
  path: 'contact',
  component: ContactComponent,
},
{
  path: 'users', 
  component: UserListComponent
},
{
  path: 'liste-personnels', 
  component: PersonnelListComponent
},
{
  path:'editsave/:id',
  component: EditUserComponent
} ,
{ path: 'foyer',
 component: FoyerComponent },

{ path: 'personnel', 
component: PersonnelComponent },
{ path: 'chambre', 
  component: ChambreComponent },

  { path: 'liste-chambres', 
    component: ChambreListComponent },
    { path: 'liste-reservations', 
      component: ReservationListComponent },

    { path: 'agent', 
    component: AgentMaintenanceComponent },

    { path: 'liste-agents', 
    component: AgentListComponent },

    { path: 'etudiant', 
      component: EtudiantComponent },
      { path: 'liste-etudiants', 
        component: EtudiantListComponent },

        { path: 'plainte', 
        component: PlaintesComponent  }, 

        { path: 'liste-plainte', 
        component: PlaintesListComponent  }, 

        { path: 'gérer-plainte', 
        component:  AgentPlainteComponent  }, 
        
        { path: 'reset-password', component: ResetPasswordComponent },

        { path: 'profil', component: ProfileComponent },
     


        { path: 'statistics', component: RoomAvailabilityComponent },

        { path: 'facture', component: FactureListComponent },

        { path: 'facture-list', component: PersonnelFactureListComponent},


        

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
