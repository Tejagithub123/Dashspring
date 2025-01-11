import { Component, OnInit } from '@angular/core';
import { FoyerService } from '../../services/foyer/foyer.service';
import { Foyer } from '../../models/foyer.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';  // Import NgbModal
import { EditFoyerModalComponent } from './EditFoyerModalComponent';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM'; // OpenStreetMap as the base layer
import { fromLonLat } from 'ol/proj'; // Helper to convert coordinates
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import { Style, Icon } from 'ol/style';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
@Component({
  selector: 'app-foyer',
  templateUrl: './foyer.component.html',
  styleUrls: ['./foyer.component.scss'],
})
export class FoyerComponent implements OnInit {
  foyers: Foyer[] = [];
  filteredFoyers: Foyer[] = []; 
  newFoyer: Foyer = { id: 0, nom: '', latitude: 0, longitude: 0, personnel: null };  
  foyerToEdit: Foyer = { id: 0, nom: '', latitude: 0, longitude: 0, personnel: null };

  searchQuery = '';  
  map: Map | undefined;


  constructor(
    private foyerService: FoyerService,
    private modalService: NgbModal  // Inject NgbModal
  ) {}

  ngOnInit(): void {
    this.loadFoyers();
    this.initializeMap();
  }

  initializeMap(): void {
    // Create the map
    this.map = new Map({
      target: 'map', // The ID of the div element in the template
      layers: [
        new TileLayer({
          source: new OSM() // OpenStreetMap as the base layer
        })
      ],
      view: new View({
        center: fromLonLat([10.1815, 36.8065]), // Center the map on Tunisia
        zoom: 6 // Adjust the zoom level
      })
    });
  }

  loadFoyers(): void {
    this.foyerService.getAllFoyers().subscribe((data) => {
      this.foyers = data;
      this.filteredFoyers = data;
      this.addFoyersToMap(); 
    });
  }

  addFoyersToMap(): void {
    if (!this.map) return;
  
    // Clear existing markers
    this.map.getLayers().forEach(layer => {
      if (layer instanceof VectorLayer) {
        this.map!.removeLayer(layer); // Remove all vector layers
      }
    });
  
    // Create a new vector source and layer
    const vectorSource = new VectorSource();
    const vectorLayer = new VectorLayer({
      source: vectorSource
    });
    this.map.addLayer(vectorLayer);
  
    // Add a marker for each foyer
    this.foyers.forEach((foyer) => {
      if (foyer.latitude && foyer.longitude) {
        const marker = new Feature({
          geometry: new Point(fromLonLat([foyer.longitude, foyer.latitude]))
        });
        marker.setStyle(
          new Style({
            image: new Icon({
              src: 'assets/marker-icon.png', // Ensure this path is correct
              scale: 0.09 // Adjust the size of the marker
            })
          })
        );
        vectorSource.addFeature(marker);
      }
    });
  }
  

  addFoyer(): void {
    this.foyerService.addFoyer(this.newFoyer).subscribe((createdFoyer) => {
      this.foyers.push(createdFoyer); 
      this.newFoyer = { id: 0, nom: '', latitude: 0, longitude: 0 , personnel: null  }; 
      this.filterFoyers();  
      this.addFoyersToMap(); 

    });
  }

  deleteFoyer(id: number | undefined): void {
    if (id) {
      this.foyerService.deleteFoyer(id).subscribe({
        next: () => {
          this.loadFoyers(); // Reload the list of foyers after successful deletion
          this.addFoyersToMap(); // Update the map after deletion
        },
        error: (err) => {
          if (err.status === 403) {
            alert('This foyer cannot be deleted because it has personnel assigned.');
          } else {
            console.error('Error deleting foyer:', err);
          }
        }
      });
    } else {
      console.error('Foyer ID is undefined');
    }
  }
  

  editFoyer(): void {
    if (this.foyerToEdit.id !== undefined) {
      // Remove personnel field from foyer before sending to the backend
      const foyerToUpdate = { ...this.foyerToEdit }; // Create a shallow copy
      foyerToUpdate.personnel = null; // Set personnel to null to prevent it from being sent
  
      console.log('Editing foyer:', foyerToUpdate);
  
      this.foyerService.updateFoyer(this.foyerToEdit.id, foyerToUpdate).subscribe(
        (updatedFoyer) => {
          console.log('Foyer updated successfully:', updatedFoyer);
          this.loadFoyers(); // Reload the list of foyers after successful update
          this.addFoyersToMap();
        },
        (error) => {
          console.error('Error updating foyer:', error);
        }
      );
    } else {
      console.error('Foyer ID is undefined, cannot update.');
    }
  }
  

selectFoyerForEditing(foyer: Foyer): void {
  this.foyerToEdit = { ...foyer };
  if (this.foyerToEdit.personnel) {
    // Ensure personnel object is being passed correctly
    console.log('Personnel assigned:', this.foyerToEdit.personnel);
  } else {
    console.log('No personnel assigned.');
  }

  const modalRef = this.modalService.open(EditFoyerModalComponent);
  modalRef.componentInstance.foyer = this.foyerToEdit;

  modalRef.componentInstance.saveFoyer.subscribe((updatedFoyer: Foyer) => {
    this.foyerToEdit = updatedFoyer;  // Update foyer in the parent
    this.editFoyer();  // Call the edit function to update via API
  });
}

  filterFoyers(): void {
    if (this.searchQuery) {
      this.filteredFoyers = this.foyers.filter(foyer =>
        foyer.nom.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    } else {
      this.filteredFoyers = this.foyers;  
    }
  }

}
