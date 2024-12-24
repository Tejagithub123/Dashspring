import { Component, Input, Output, EventEmitter } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Foyer } from '../../models/foyer.model';  

@Component({
  selector: 'app-edit-foyer-modal',
  templateUrl: './edit-foyer-modal.component.html',
})
export class EditFoyerModalComponent {
  @Input() foyer: Foyer = { id: 0, nom: '', longitude: 0.0, latitude: 0.0 };
  @Output() saveFoyer = new EventEmitter<Foyer>();  

  constructor(public activeModal: NgbActiveModal) {}

  saveChanges() {
    if (this.foyer) {
      // Emit the updated foyer object to the parent component
      this.saveFoyer.emit(this.foyer);
      this.activeModal.close();  // Close the modal
    } else {
      console.error('Foyer is not defined');
    }
  }
}
