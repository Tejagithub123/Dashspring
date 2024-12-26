import { Component, OnInit } from '@angular/core';
import { PersonnelService } from 'src/app/services/personnel/personnel.service';
import { FoyerService } from 'src/app/services/foyer/foyer.service'; // Import FoyerService
import { Personnel } from 'src/app/models/personnel.model';
import { Foyer } from 'src/app/models/foyer.model'; // Import Foyer model
import { Router } from '@angular/router';

@Component({
  selector: 'app-personnel-list',
  templateUrl: './personnel-list-component.component.html',
  styleUrls: ['./personnel-list-component.component.scss'],
})
export class PersonnelListComponent implements OnInit {
  personnels: Personnel[] = [];
  foyers: Foyer[] = [];

  constructor(
    private personnelService: PersonnelService,
    private foyerService: FoyerService, // Inject FoyerService
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadPersonnelsAndFoyers();
  }

  // Fetch personnel and foyer data
  loadPersonnelsAndFoyers(): void {
    this.personnelService.getAll().subscribe(
      (personnelData) => {
        this.personnels = personnelData;
        this.foyerService.getAllFoyers().subscribe(
          (foyerData) => {
            this.foyers = foyerData;
            this.mapFoyersToPersonnels(); // Map foyers to personnels
          },
          (error) => {
            console.error('Error fetching foyers:', error);
          }
        );
      },
      (error) => {
        console.error('Error fetching personnel list:', error);
        alert('Failed to fetch personnel list.');
      }
    );
  }

  // Match personnel with their corresponding foyers
  mapFoyersToPersonnels(): void {
    this.personnels.forEach((personnel) => {
      const matchingFoyer = this.foyers.find(
        (foyer) => foyer.personnel?.id === personnel.id
      );
      if (matchingFoyer) {
        personnel.foyerName = matchingFoyer.nom; // Add foyer name to personnel
      } else {
        personnel.foyerName = 'Not Assigned';
      }
    });
  }

  // Delete personnel by id
  deletePersonnel(id: number): void {
    if (id !== undefined && id !== null) {
      if (confirm('Are you sure you want to delete this personnel?')) {
        this.personnelService.delete(id).subscribe(
          () => {
            alert('Personnel deleted successfully!');
            this.loadPersonnelsAndFoyers();
          },
          (error) => {
            console.error('Error deleting personnel:', error);
            alert('Failed to delete personnel.');
          }
        );
      }
    }
  }

  // Navigate to update form
  updatePersonnel(id: number): void {
    if (id !== undefined && id !== null) {
      this.router.navigate([`/personnel/${id}`]);
    }
  }
}
