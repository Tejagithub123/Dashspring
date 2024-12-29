import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Etudiant } from 'src/app/models/etudiant.model';
import { EtudiantService } from 'src/app/services/etudiant/etudiant.service';
import { FoyerService } from 'src/app/services/foyer/foyer.service';
import { UserStorageService } from 'src/app/storage/user-storage.service';

@Component({
  selector: 'app-etudiant-profile',
  templateUrl: './etudiant.component.html',
  styleUrls: ['./etudiant.component.css'],
})
export class EtudiantComponent implements OnInit {
  etudiant: Etudiant = {
    nom: '',
    prenom: '',
    email: '',
    dateNaissance: '',
    mdp: '',
    cin: 0,
  };

  foyers: any[] = [];

  constructor(
    private etudiantService: EtudiantService,
    private route: ActivatedRoute,
    private foyerService: FoyerService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  save(): void {
    console.log('Saving etudiant:', this.etudiant);

    // Create a foyer object with just the id if a foyerId is present
    const etudiantToSend = { ...this.etudiant,};
      // Create a new etudiant
      this.etudiantService.create(etudiantToSend).subscribe(
        () => {
          console.log('Etudiant created successfully!');
          alert('Etudiant created successfully!');
          if (UserStorageService.getUserRole() === "Role_ADMIN"){
            this.router.navigate(['/liste-personnels']);
          }
          else{window.location.reload()}
        },
        (error) => {
          console.error('Error creating etudiant:', error);
          alert('Failed to create etudiant.');
        }
      );
    }
  
  }

