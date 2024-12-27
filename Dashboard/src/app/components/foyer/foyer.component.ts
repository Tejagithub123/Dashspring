import { Component, OnInit } from '@angular/core';
import { FoyerService } from '../../services/foyer/foyer.service';
import { Foyer } from '../../models/foyer.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';  // Import NgbModal
import { EditFoyerModalComponent } from './EditFoyerModalComponent';

@Component({
  selector: 'app-foyer',
  templateUrl: './foyer.component.html',
  styleUrls: ['./foyer.component.scss'],
})
export class FoyerComponent implements OnInit {
  foyers: Foyer[] = [];
  filteredFoyers: Foyer[] = []; 
  newFoyer: Foyer = { id: 0, nom: '', latitude: 0, longitude: 0, personnel: null };  
  foyerToEdit: Foyer = { id: 0, nom: '', latitude: 0, longitude: 0, personnel: null };

  searchQuery = ''; 

  constructor(
    private foyerService: FoyerService,
    private modalService: NgbModal  // Inject NgbModal
  ) {}

  ngOnInit(): void {
    this.loadFoyers();
  }

  loadFoyers(): void {
    this.foyerService.getAllFoyers().subscribe((data) => {
      this.foyers = data;
      this.filteredFoyers = data;  
    });
  }

  addFoyer(): void {
    this.foyerService.addFoyer(this.newFoyer).subscribe((createdFoyer) => {
      this.foyers.push(createdFoyer); 
      this.newFoyer = { id: 0, nom: '', latitude: 0, longitude: 0 , personnel: null  }; 
      this.filterFoyers();  
    });
  }

  deleteFoyer(id: number | undefined): void {
    if (id) {
      this.foyerService.deleteFoyer(id).subscribe({
        next: () => {
          this.loadFoyers(); // Reload the list of foyers after successful deletion
        },
        error: (err) => {
          if (err.status === 403) {
            alert('This foyer cannot be deleted because it has personnel assigned.');
          } else {
            console.error('Error deleting foyer:', err);
          }
        }
      });
    } else {
      console.error('Foyer ID is undefined');
    }
  }
  

  editFoyer(): void {
    if (this.foyerToEdit.id !== undefined) {
      // Remove personnel field from foyer before sending to the backend
      const foyerToUpdate = { ...this.foyerToEdit }; // Create a shallow copy
      foyerToUpdate.personnel = null; // Set personnel to null to prevent it from being sent
  
      console.log('Editing foyer:', foyerToUpdate);
  
      this.foyerService.updateFoyer(this.foyerToEdit.id, foyerToUpdate).subscribe(
        (updatedFoyer) => {
          console.log('Foyer updated successfully:', updatedFoyer);
          this.loadFoyers(); // Reload the list of foyers after successful update
        },
        (error) => {
          console.error('Error updating foyer:', error);
        }
      );
    } else {
      console.error('Foyer ID is undefined, cannot update.');
    }
  }
  

selectFoyerForEditing(foyer: Foyer): void {
  this.foyerToEdit = { ...foyer };
  if (this.foyerToEdit.personnel) {
    // Ensure personnel object is being passed correctly
    console.log('Personnel assigned:', this.foyerToEdit.personnel);
  } else {
    console.log('No personnel assigned.');
  }

  const modalRef = this.modalService.open(EditFoyerModalComponent);
  modalRef.componentInstance.foyer = this.foyerToEdit;

  modalRef.componentInstance.saveFoyer.subscribe((updatedFoyer: Foyer) => {
    this.foyerToEdit = updatedFoyer;  // Update foyer in the parent
    this.editFoyer();  // Call the edit function to update via API
  });
}

  filterFoyers(): void {
    if (this.searchQuery) {
      this.filteredFoyers = this.foyers.filter(foyer =>
        foyer.nom.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    } else {
      this.filteredFoyers = this.foyers;  
    }
  }

}
