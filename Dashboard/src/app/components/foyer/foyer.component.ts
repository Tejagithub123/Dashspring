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
      this.foyerService.deleteFoyer(id).subscribe(() => {
        this.loadFoyers(); 
      });
    } else {
      console.error('Foyer ID is undefined');
    }
  }

 // Function to update foyer
 editFoyer(): void {
  if (this.foyerToEdit.id !== undefined) {
    console.log('Editing foyer:', this.foyerToEdit);
    this.foyerService.updateFoyer(this.foyerToEdit.id, this.foyerToEdit).subscribe(
      (updatedFoyer) => {
        console.log('Foyer updated successfully:', updatedFoyer);
        this.loadFoyers();
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
  // Open the modal programmatically
  const modalRef = this.modalService.open(EditFoyerModalComponent);
  modalRef.componentInstance.foyer = this.foyerToEdit;

  // Listen for the saveFoyer event from the modal and update the foyer
  modalRef.componentInstance.saveFoyer.subscribe((updatedFoyer: Foyer) => {
    this.foyerToEdit = updatedFoyer;  // Update the foyer in the parent
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
