import { Component, OnInit } from '@angular/core';;
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Chambre } from 'src/app/models/chambre.model';
import { ChambreService } from 'src/app/services/chambre/chambre.service';
import { UserStorageService } from 'src/app/storage/user-storage.service';

@Component({
  selector: 'app-chambre-list',
  templateUrl: './chambre-list-component.component.html',
  styleUrls: ['./chambre-list-component.component.scss'],
})
export class ChambreListComponent  implements OnInit {
  chambres: Chambre[] = [];
  selectedChambre!: Chambre;
  originalChambre!: Chambre; 
  constructor(
    private chambreService: ChambreService,
    private modalService: NgbModal 
  ) {}

  ngOnInit(): void {
    this.loadPersonnelsAndFoyers();
  }

  
  loadPersonnelsAndFoyers(): void {
    this.chambreService.getAll(Number(UserStorageService.getFoyer_Id())).subscribe((chambres) => {
      this.chambres = chambres;
    });
  }

  


openUpdateModal(content: any, chambre: Chambre): void {
  
  this.originalChambre = { ...chambre };
  console.log('Original Chambre:', this.originalChambre);
  
  this.selectedChambre = { ...chambre };

  
  this.modalService.open(content, { size: 'lg' });
}

updatePersonnel(): void {
  if (this.selectedChambre.id) {
    
    
    const updatePayload: any = {};

    
    if (this.selectedChambre.name !== this.originalChambre.name) {
      updatePayload.name = this.selectedChambre.name;
    }
    
    if (this.selectedChambre.description !== this.originalChambre.description) {
      updatePayload.description = this.selectedChambre.description;
    }
    
    if (this.selectedChambre.type !== this.originalChambre.type) {
      updatePayload.type = this.selectedChambre.type;
    }
    
    if (this.selectedChambre.availble !== this.originalChambre.availble) {
      updatePayload.availble = this.selectedChambre.availble;
    }
    
    if (this.selectedChambre.price !== this.originalChambre.price) {
      updatePayload.price = this.selectedChambre.price;
    }
    console.log('Update Payload before sending to API:', updatePayload);


    
    this.chambreService.update(this.selectedChambre.id, updatePayload).subscribe(
      () => {
        alert('Chambre updated successfully!');
        this.loadPersonnelsAndFoyers();
      },
      (error) => {
        console.error('Update failed:', error);
        alert('Failed to update chambre');
      }
    );
  }
}


  deletePersonnel(id: number): void {
    console.log(id)
    if (confirm('Are you sure you want to delete this chambre?')) {
      this.chambreService.delete(id).subscribe(() => {
        alert('Personnel deleted successfully!');
        this.loadPersonnelsAndFoyers();
      });
    }
  }
}
