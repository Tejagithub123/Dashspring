import { Component, OnInit } from '@angular/core';
import { FoyerService } from '../../services/foyer.service';
import { Foyer } from '../../models/foyer.model';

@Component({
  selector: 'app-foyer',
  templateUrl: './foyer.component.html',
  styleUrls: ['./foyer.component.css'],
})
export class FoyerComponent implements OnInit {
  foyers: Foyer[] = [];
  newFoyer: Foyer = { id: 0, nom: '' };

  constructor(private foyerService: FoyerService) {}

  ngOnInit(): void {
    this.loadFoyers();
  }

  loadFoyers(): void {
    this.foyerService.getAllFoyers().subscribe((data) => {
      this.foyers = data;
    });
  }

  addFoyer(): void {
    this.foyerService.addFoyer(this.newFoyer).subscribe((createdFoyer) => {
      this.foyers.push(createdFoyer); // Add new foyer to list
      this.newFoyer = { id: 0, nom: '' }; // Reset form
    });
  }

  deleteFoyer(id: number | undefined): void {
    if (id) {
      this.foyerService.deleteFoyer(id).subscribe(() => {
        this.loadFoyers(); // Refresh the list of foyers after deletion
      });
    } else {
      console.error('Foyer ID is undefined');
    }
  }
  
  
  
}
