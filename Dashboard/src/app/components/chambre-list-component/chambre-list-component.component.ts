import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Chambre } from 'src/app/models/chambre.model';
import { ChambreService } from 'src/app/services/chambre/chambre.service';
import { UserStorageService } from 'src/app/storage/user-storage.service';

@Component({
  selector: 'app-chambre-list',
  templateUrl: './chambre-list-component.component.html',
  styleUrls: ['./chambre-list-component.component.scss'],
})
export class ChambreListComponent implements OnInit {
  chambres: Chambre[] = [];
  filteredChambres: Chambre[] = [];
  selectedChambre!: Chambre;
  originalChambre!: Chambre;
  role: string = UserStorageService.getUserRole();
  
  // Filter properties
  chambreNameFilter: string = '';
  foyerNameFilter: string = '';
  typeFilter: string = '';

  constructor(
    private chambreService: ChambreService,
    private modalService: NgbModal 
  ) {}

  ngOnInit(): void {
    this.loadChambres();
    this.role = UserStorageService.getUserRole(); // Get role on initialization
  }

  loadChambres(): void {
    if (this.role === 'ROLE_PERSONNEL') {
      this.chambreService.getAll(Number(UserStorageService.getFoyer_Id())).subscribe((chambres) => {
        this.chambres = chambres;
        console.log(chambres); 
        this.filteredChambres = [...this.chambres]; // No filter for personnel
      });
    } else {
      this.chambreService.getAllChambre().subscribe((chambres) => {
        console.log(chambres); 
        this.chambres = chambres;
        this.applyFilters(); // Apply filters when loading data for other roles
      });
    }
  }

  // Filter function
  applyFilters(): void {
    this.filteredChambres = this.chambres.filter(chambre => {
      const matchesFoyerName = chambre.foyerNom?.toLowerCase().includes(this.foyerNameFilter.toLowerCase());
      const matchesType = chambre.type.toLowerCase().includes(this.typeFilter.toLowerCase());
      
      // Only filter based on availability if the role is not 'ROLE_PERSONNEL'
      if (this.role !== 'ROLE_PERSONNEL') {
        const isAvailable = chambre.availble === true;
        return matchesFoyerName && matchesType && isAvailable;
      } else {
        // For ROLE_PERSONNEL, no availability filter applied
        return matchesFoyerName && matchesType;
      }
    });
  }

  // Update filter whenever input changes
  onFilterChange(): void {
    this.applyFilters();
  }

  openUpdateModal(content: any, chambre: Chambre): void {
    this.originalChambre = { ...chambre };
    this.selectedChambre = { ...chambre };
    this.modalService.open(content, { size: 'lg' });
  }

  updateChambre(): void {
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

      this.chambreService.update(this.selectedChambre.id, updatePayload).subscribe(
        () => {
          alert('Chambre updated successfully!');
          this.loadChambres();
        },
        (error) => {
          console.error('Update failed:', error);
          alert('Failed to update chambre');
        }
      );
    }
  }

  deleteChambre(id: number): void {
    if (confirm('Are you sure you want to delete this chambre?')) {
      this.chambreService.delete(id).subscribe(() => {
        alert('Chambre deleted successfully!');
        this.loadChambres();
      });
    }
  }
}
