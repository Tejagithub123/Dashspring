import { Component, OnInit } from '@angular/core';
import { Facture } from 'src/app/models/facture.model';
import { FactureService } from 'src/app/services/Facture/facture.service';
import { UserStorageService } from 'src/app/storage/user-storage.service';

@Component({
  selector: 'app-personnel-facture-list',
  templateUrl: './personnel-facture-list.component.html',
  styleUrls: ['./personnel-facture-list.component.scss'],
})
export class PersonnelFactureListComponent implements OnInit {
  factures: Facture[] = [];

  constructor(
    private factureService: FactureService,
    private userStorageService: UserStorageService
  ) {}

  ngOnInit(): void {
    this.loadFacturesByFoyerId();
  }

  // Fetch factures by foyerId
  loadFacturesByFoyerId(): void {
    const foyerId = Number(UserStorageService.getFoyer_Id()); // Get foyerId from storage
    if (foyerId) {
      this.factureService.getFacturesByFoyerId(foyerId).subscribe(
        (factures) => {
          this.factures = factures;
        },
        (error) => {
          console.error('Error fetching invoices:', error);
        }
      );
    } else {
      console.error('Foyer ID not found in storage.');
    }
  }
}