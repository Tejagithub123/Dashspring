import { Component, OnInit } from '@angular/core';
import { EtudiantService } from 'src/app/services/etudiant/etudiant.service';
import { FoyerService } from 'src/app/services/foyer/foyer.service';
import { Etudiant } from 'src/app/models/etudiant.model';
import { Foyer } from 'src/app/models/foyer.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-etudiant-list',
  templateUrl: './etudiant-list-component.component.html',
  styleUrls: ['./etudiant-list-component.component.scss'],
})
export class EtudiantListComponent implements OnInit {
  etudiants: Etudiant[] = [];
  foyers: Foyer[] = [];
  unassignedFoyers: Foyer[] = [];
  selectedEtudiant!: Etudiant;
  originalEtudiant!: Etudiant;

  constructor(
    private etudiantService: EtudiantService,
    private foyerService: FoyerService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.loadEtudiantsAndFoyers();
  }

  loadEtudiantsAndFoyers(): void {
    this.etudiantService.getAll().subscribe((etudiants) => {
      this.etudiants = etudiants;
    });
  }

  openUpdateModal(content: any, etudiant: Etudiant): void {
    this.originalEtudiant = { ...etudiant };
    this.selectedEtudiant = { ...etudiant };
    this.modalService.open(content, { size: 'lg' });
  }

  updateEtudiant(): void {
    if (this.selectedEtudiant.id) {
      // Create a payload with only the updated fields
      const payload: Partial<Etudiant> = {
        nom: this.selectedEtudiant.nom,
        prenom: this.selectedEtudiant.prenom,
        email: this.selectedEtudiant.email,
        dateNaissance: this.selectedEtudiant.dateNaissance,
        cin: this.selectedEtudiant.cin,
      };
  
      this.etudiantService.update(this.selectedEtudiant.id, payload).subscribe(
        (updatedEtudiant) => {
          // Update the etudiant in the list
          const index = this.etudiants.findIndex(e => e.id === updatedEtudiant.id);
          if (index !== -1) {
            this.etudiants[index] = updatedEtudiant;
          }
          // Close the modal
          this.modalService.dismissAll();
          alert('Etudiant updated successfully!');
        },
        (error) => {
          console.error('Error updating etudiant:', error);
          alert('Failed to update etudiant.');
        }
      );
    }
  }
  

  deleteEtudiant(id: number): void {
    if (confirm('Are you sure you want to delete this etudiant?')) {
      this.etudiantService.delete(id).subscribe(() => {
        alert('Etudiant deleted successfully!');
        this.loadEtudiantsAndFoyers();
      });
    }
  }
}