import { Component, OnInit } from '@angular/core';
import { FoyerService } from '../../services/foyer/foyer.service';
import { Foyer } from '../../models/foyer.model';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-foyer',
  templateUrl: './foyer.component.html',
  styleUrls: ['./foyer.component.scss'],
})
export class FoyerComponent implements OnInit {
  foyers: Foyer[] = [];
  filteredFoyers: Foyer[] = []; // Array to store filtered foyers
  newFoyer: Foyer = { id: 0, nom: '', latitude: 0, longitude: 0 };  // Include latitude and longitude
  searchQuery = ''; // Search query for filtering

  constructor(private foyerService: FoyerService) {}

  ngOnInit(): void {
    this.loadFoyers();
  }

  loadFoyers(): void {
    this.foyerService.getAllFoyers().subscribe((data) => {
      this.foyers = data;
      this.filteredFoyers = data;  // Set filteredFoyers to all foyers initially
    });
  }

  addFoyer(): void {
    this.foyerService.addFoyer(this.newFoyer).subscribe((createdFoyer) => {
      this.foyers.push(createdFoyer); // Add new foyer to list
      this.newFoyer = { id: 0, nom: '', latitude: 0, longitude: 0 }; // Reset form
      this.filterFoyers();  // Apply filtering after adding
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

  // Update Foyer functionality (you can implement as needed)
  editFoyer(foyer: Foyer): void {
    // For now, just log the foyer
    console.log('Editing foyer:', foyer);
    // Implement the update logic here
  }

  // Filter foyers based on search query
  filterFoyers(): void {
    if (this.searchQuery) {
      this.filteredFoyers = this.foyers.filter(foyer =>
        foyer.nom.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    } else {
      this.filteredFoyers = this.foyers;  // Show all if search query is empty
    }
  }
}
