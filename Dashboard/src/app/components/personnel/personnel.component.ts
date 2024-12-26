import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Personnel } from 'src/app/models/personnel.model';
import { PersonnelService } from 'src/app/services/personnel/personnel.service';
import { FoyerService } from 'src/app/services/foyer/foyer.service'; 
import { Router } from '@angular/router';
@Component({
  selector: 'app-personnel-profile',
  templateUrl: './personnel.component.html',
  styleUrls: ['./personnel.component.css'],
})
export class PersonnelComponent implements OnInit {
  personnel: Personnel = {
    nom: '',
    prenom: '',
    email: '',
    dateNaissance: '',
    mdp: '',
    foyerId: 0,
    cin : 0
  };

  foyers: any[] = []; 

  constructor(
    private personnelService: PersonnelService,
    private route: ActivatedRoute,
    private foyerService: FoyerService ,
    private router: Router) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.personnelService.getById(id).subscribe(
        (data) => {
          this.personnel = data;
        },
        (error) => {
          console.error('Error fetching personnel:', error);
          alert('Failed to fetch personnel data.');
        }
      );
    }

    
    this.foyerService.getAllFoyers().subscribe(
      (data) => {
        
        this.foyers = data.filter(foyer => !foyer.personnel );
      },
      (error) => {
        console.error('Error fetching foyers:', error);
        alert('Failed to fetch foyers.');
      }
    );
  }

  save(): void {
    console.log('Saving personnel:', this.personnel);
  
    // Create a foyer object with just the id
    const foyerObject = this.personnel.foyerId ? { id: this.personnel.foyerId } : null;
    const personnelToSend = { ...this.personnel, foyer: foyerObject };
  
    if (this.personnel && this.personnel.id) {
      // If the personnel already has an id, update the existing record
      console.log('Updating personnel with id:', this.personnel.id);
      
      this.personnelService.update(this.personnel.id, personnelToSend).subscribe(
        () => {
          console.log('Profile updated successfully!');
          alert('Profile updated successfully!');
        },
        (error) => {
          console.error('Error updating profile:', error);
          alert('Failed to update profile.');
        }
      );
    } else {
      // If the personnel does not have an id, create a new record
      console.log('Creating new personnel with foyerId:', this.personnel.foyerId);
      
      this.personnelService.create(personnelToSend).subscribe(
        () => {
          console.log('Personnel created successfully!');
          alert('Personnel created successfully!');
          this.router.navigate(['/liste-personnels']);
        },
        (error) => {
          console.error('Error creating personnel:', error);
          alert('Failed to create personnel.');
        }
      );
    }
  }
  
  

}
