import { Component, OnInit } from '@angular/core';
import { PersonnelService } from 'src/app/services/personnel/personnel.service';
import { FoyerService } from 'src/app/services/foyer/foyer.service';
import { Personnel } from 'src/app/models/personnel.model';
import { Foyer } from 'src/app/models/foyer.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-personnel-list',
  templateUrl: './personnel-list-component.component.html',
  styleUrls: ['./personnel-list-component.component.scss'],
})
export class PersonnelListComponent implements OnInit {
  personnels: Personnel[] = [];
  foyers: Foyer[] = [];
  unassignedFoyers: Foyer[] = [];
  selectedPersonnel!: Personnel;
  originalPersonnel!: Personnel; 
  constructor(
    private personnelService: PersonnelService,
    private foyerService: FoyerService,
    private modalService: NgbModal 
  ) {}

  ngOnInit(): void {
    this.loadPersonnelsAndFoyers();
  }

  
  loadPersonnelsAndFoyers(): void {
    this.personnelService.getAll().subscribe((personnels) => {
      this.personnels = personnels;
      this.foyerService.getAllFoyers().subscribe((foyers) => {
        this.foyers = foyers;
        this.unassignedFoyers = foyers.filter((foyer) => !foyer.personnel);
        this.mapFoyersToPersonnels();
      });
    });
  }

  
  mapFoyersToPersonnels(): void {
    this.personnels.forEach((personnel) => {
      const foyer = this.foyers.find((f) => f.personnel?.id === personnel.id);
      personnel.foyerName = foyer ? foyer.nom : 'Not Assigned';
    });
  }


openUpdateModal(content: any, personnel: Personnel): void {
  
  this.originalPersonnel = { ...personnel };
  console.log('Original Personnel:', this.originalPersonnel);
  
  this.selectedPersonnel = { ...personnel };

  
  this.modalService.open(content, { size: 'lg' });
}

updatePersonnel(): void {
  if (this.selectedPersonnel.id) {
    
    
    const updatePayload: any = {};

    
    if (this.selectedPersonnel.nom !== this.originalPersonnel.nom) {
      updatePayload.nom = this.selectedPersonnel.nom;
    }

    if (this.selectedPersonnel.prenom !== this.originalPersonnel.prenom) {
      updatePayload.prenom = this.selectedPersonnel.prenom;
    }

    if (this.selectedPersonnel.email !== this.originalPersonnel.email) {
      updatePayload.email = this.selectedPersonnel.email;
    }

    if (this.selectedPersonnel.dateNaissance !== this.originalPersonnel.dateNaissance) {
      updatePayload.dateNaissance = this.selectedPersonnel.dateNaissance;
    }

    
    if (this.selectedPersonnel.cin !== this.originalPersonnel.cin) {
      updatePayload.cin = this.selectedPersonnel.cin;

    }

    
    if (this.selectedPersonnel.foyerId !== undefined && this.selectedPersonnel.foyerId !== null) {
      if (this.selectedPersonnel.foyerId !== this.originalPersonnel.foyerId) {
        updatePayload.foyer = { id: this.selectedPersonnel.foyerId };
      }
    } else if (this.selectedPersonnel.foyer && this.selectedPersonnel.foyer.id) {
      if (this.selectedPersonnel.foyer.id !== this.originalPersonnel.foyer?.id) {
        updatePayload.foyer = { id: this.selectedPersonnel.foyer.id };
      }
    } else {
      updatePayload.foyer = { id: null }; // if no foyer selected, send it as null
    }
    
    
    console.log('Update Payload before sending to API:', updatePayload);


    
    this.personnelService.update(this.selectedPersonnel.id, updatePayload).subscribe(
      () => {
        alert('Personnel updated successfully!');
        this.loadPersonnelsAndFoyers();
      },
      (error) => {
        console.error('Update failed:', error);
        alert('Failed to update personnel');
      }
    );
  }
}


  deletePersonnel(id: number): void {
    if (confirm('Are you sure you want to delete this personnel?')) {
      this.personnelService.delete(id).subscribe(() => {
        alert('Personnel deleted successfully!');
        this.loadPersonnelsAndFoyers();
      });
    }
  }
}
