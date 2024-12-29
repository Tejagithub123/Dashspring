import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap'; // Import NgbActiveModal

@Component({
  selector: 'app-success-modal',
  templateUrl: './success-modal.component.html',
  styleUrls: ['./success-modal.component.css'],
})
export class SuccessModalComponent {
  @Input() message: string = ''; // The message to display
  @Input() type: string = ''; // Success or fail

  constructor(public activeModal: NgbActiveModal) {} // Inject NgbActiveModal
}
