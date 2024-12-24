import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Personnel } from 'src/app/models/personnel.model';
import { PersonnelService } from 'src/app/services/personnel/personnel.service';
import { FoyerService } from 'src/app/services/foyer/foyer.service'; // Service to get foyers

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

  foyers: any[] = []; // List of foyers to populate the dropdown

  constructor(
    private personnelService: PersonnelService,
    private route: ActivatedRoute,
    private foyerService: FoyerService // Inject the Foyer service
  ) {}

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

    // Fetch foyers to populate the dropdown
    this.foyerService.getAllFoyers().subscribe(
      (data) => {
        // Filter foyers to only include those without any assigned personnel
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

    if (this.personnel && this.personnel.id) {
      // If the personnel already has an id, update the existing record
      console.log('Updating personnel with id:', this.personnel.id);
      
      this.personnelService.update(this.personnel.id, this.personnel).subscribe(
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
      const foyerId = this.personnel.foyerId;
      console.log('Foyer ID:', foyerId);

      if (foyerId !== undefined) {  // Check if foyerId is defined
        console.log('Creating new personnel with foyerId:', foyerId);
        
        this.personnelService.create(this.personnel, foyerId).subscribe(
          () => {
            console.log('Personnel created successfully!');
            alert('Personnel created successfully!');
          },
          (error) => {
            console.error('Error creating personnel:', error);
            alert('Failed to create personnel.');
          }
        );
      } else {
        console.log('Foyer ID is missing, cannot create personnel');
        alert('Foyer ID is required to create personnel.');
      }
    }
  }
}
