import { Component, OnInit } from '@angular/core';
import { Facture } from 'src/app/models/facture.model';
import { FactureService } from 'src/app/services/Facture/facture.service';
import { UserStorageService } from 'src/app/storage/user-storage.service';
@Component({
  selector: 'app-facture-list',
  templateUrl: './facture-list.component.html',
  styleUrls: ['./facture-list.component.scss'],
})
export class FactureListComponent implements OnInit {
  factures: Facture[] = [];
  userId: number;

  constructor(
    private factureService: FactureService,
    private userStorageService: UserStorageService
  ) {
    this.userId = Number(UserStorageService.getUserId());
  }

  ngOnInit(): void {
    this.loadFactures();
  }

  // Fetch invoices for the logged-in student
  loadFactures(): void {
    this.factureService.getFacturesByEtudiant(this.userId).subscribe(
      (factures) => {
        this.factures = factures;
      },
      (error) => {
        console.error('Error fetching invoices:', error);
      }
    );
  }

  // Mark an invoice as paid
  payerFacture(factureId: number): void {
    this.factureService.markAsPaid(factureId).subscribe(
      () => {
        alert('Payment successful!');
        this.loadFactures(); // Refresh the list of invoices
      },
      (error) => {
        console.error('Error paying invoice:', error);
        alert('Failed to pay invoice.');
      }
    );
  }
}