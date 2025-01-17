import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SidebarComponent } from './sidebar1/sidebar.component';
import { WigetsComponent } from './wigets/wigets.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HomeComponent } from './home/home.component';
import { ContactComponent } from './contact/contact.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { UserListComponent } from './user-list/user-list.component';
import { UserService } from './user-service.service';
import { ChartsModule } from 'ng2-charts';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import {ToggleService} from './toggle.service';
import { EditUserComponent } from './edit-user/edit-user.component';
import { FoyerComponent } from './components/foyer/foyer.component';
import { PersonnelComponent } from './components/personnel/personnel.component';
import { ChambreComponent } from './components/chambre/chambre.component';
import { EtudiantComponent } from './components/etudiant/etudiant.component';
import { LoginComponent } from './components/login/login.component'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EditFoyerModalComponent } from './components/foyer/EditFoyerModalComponent';
import { SuccessModalComponent } from './components/personnel/SuccessModalComponent';
// Angular Material Modules
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { PersonnelListComponent } from './components/personnel-list-component/personnel-list-component.component';
import { ChambreListComponent } from './components/chambre-list-component/chambre-list-component.component';

import { AgentMaintenanceComponent } from './components/add-agent/add-agent.component';
import { AgentListComponent } from './components/agent-list/agent-list.component';

import{EtudiantListComponent} from './components/etudiant-list-component/etudiant-list-component.component';
import { PlaintesComponent } from './components/plaintes/plaintes.component';

import { ReactiveFormsModule } from '@angular/forms';
import { PlaintesListComponent } from './components/plaintes-list/plaintes-list.component';
import { AgentPlainteComponent } from './components/agent-plainte/agent-plainte.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { ProfileComponent } from './components/profile/profile.component';

import { ReservationListComponent } from './components/reservation-list-component/reservation-list-component.component';
import { RoomAvailabilityComponent } from './components/room-availability/room-availability.component';
import { FactureListComponent } from './components/facture-list/facture-list.component';
import { PersonnelFactureListComponent } from './components/personnel-facture-list/personnel-facture-list.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SidebarComponent,
    WigetsComponent,
    HomeComponent,
    ContactComponent,
    UserListComponent,
    EditUserComponent,
    FoyerComponent,
    PersonnelComponent,
    LoginComponent , 
    EditFoyerModalComponent,
    SuccessModalComponent,
    PersonnelListComponent,
    ChambreComponent,

    ChambreListComponent,
    ReservationListComponent,
    AgentMaintenanceComponent,
    AgentListComponent,

   
    EtudiantComponent,
    EtudiantListComponent,
    PlaintesComponent,
    PlaintesListComponent,
    AgentPlainteComponent,
    ResetPasswordComponent,
    ProfileComponent,
    RoomAvailabilityComponent,
    FactureListComponent,
    PersonnelFactureListComponent,
   
   
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FontAwesomeModule,
    HttpClientModule,
    FormsModule,
    ChartsModule,
    MatDividerModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatListModule,
    MatMenuModule,
    MatSidenavModule ,
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule, // Required for Angular Material animations
    MatInputModule,
    MatFormFieldModule,
    MatCardModule,
    PerfectScrollbarModule ,
    ReactiveFormsModule,  // Ajoutez ReactiveFormsModule ici
    

  ],
  providers: [UserService, ToggleService],
  bootstrap: [AppComponent]
})
export class AppModule { }
