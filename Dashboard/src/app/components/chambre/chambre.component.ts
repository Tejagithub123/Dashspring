import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Chambre } from 'src/app/models/chambre.model';
import { ChambreService } from 'src/app/services/chambre/chambre.service';
import { FoyerService } from 'src/app/services/foyer/foyer.service'; 
import { Router } from '@angular/router';
import { UserStorageService } from 'src/app/storage/user-storage.service';
@Component({
  selector: 'app-chambre-profile',
  templateUrl: './chambre.component.html',
  styleUrls: ['./chambre.component.css'],
})
export class ChambreComponent implements OnInit {
  chambre: Chambre = {
    name:'',
    description: '',
    type: 'SINGLE',
    availble: true,
    price: '',
  };

  foyers: any[] = []; 

  constructor(
    private chambreService: ChambreService,
    private route: ActivatedRoute,
    private foyerService: FoyerService ,
    private router: Router) {}

  ngOnInit(): void {
 }

  save(): void {
    
    console.log('Saving chambre:', this.chambre);
  
    // Create a foyer object with just the id
  
    const chambreToSend = { ...this.chambre};
  
    if (this.chambre && this.chambre.id) {
      // If the chambre already has an id, update the existing record
      console.log('Updating chambre with id:', this.chambre.id);
      
      this.chambreService.update(this.chambre.id, chambreToSend).subscribe(
        () => {
          console.log('Profile updated successfully!');
          alert('Profile updated successfully!');
        },
        (error) => {
          console.error('Error updating profile:', error);
          alert('Failed to update profile.');
        }
      );
    } else {
      // If the chambre does not have an id, create a new record
      console.log('Creating new chambre with foyerId:', this.chambre.foyerId);
      
      this.chambreService.create(chambreToSend,Number(UserStorageService.getFoyer_Id())).subscribe(
        () => {
          console.log('Personnel created successfully!');
          alert('Personnel created successfully!');
          this.router.navigate(['/liste-chambres']);
        },
        (error) => {
          console.error('Error creating chambre:', error);
          alert('Failed to create chambre.');
        }
      );
    }
  }
  
  

}
