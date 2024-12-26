import { Component, OnInit } from '@angular/core';
import { PersonnelService } from 'src/app/services/personnel/personnel.service';
import { Personnel } from 'src/app/models/personnel.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-personnel-list',
  templateUrl: './personnel-list-component.component.html',
  styleUrls: ['./personnel-list-component.component.scss'],
})
export class PersonnelListComponent implements OnInit {
  personnels: Personnel[] = []; // List of personnel

  constructor(
    private personnelService: PersonnelService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadPersonnels();
  }

  // Fetch all personnel from the service
  loadPersonnels(): void {
    this.personnelService.getAll().subscribe(
      (data) => {
        this.personnels = data;
        console.log(data)
      },
      (error) => {
        console.error('Error fetching personnel list:', error);
        alert('Failed to fetch personnel list.');
      }
    );
  }

  // Delete personnel by id
  deletePersonnel(id: number): void {
    if (id !== undefined && id !== null) { // Make sure id is a valid number
      if (confirm('Are you sure you want to delete this personnel?')) {
        this.personnelService.delete(id).subscribe(
          () => {
            console.log('Personnel deleted successfully!');
            alert('Personnel deleted successfully!');
            this.loadPersonnels(); // Reload the personnel list after deletion
          },
          (error) => {
            console.error('Error deleting personnel:', error);
            alert('Failed to delete personnel.');
          }
        );
      }
    }
  }

  // Navigate to the update form for the selected personnel
  updatePersonnel(id: number): void {
    if (id !== undefined && id !== null) { // Make sure id is a valid number
      this.router.navigate([`/personnel/${id}`]); // Redirect to the personnel profile page for updating
    }
  }
}
