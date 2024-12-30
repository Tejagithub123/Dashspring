import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Plainte } from '../../models/plainte.model';
import { PlainteService } from '../../services/plainte/plainte.service';
import { AgentMaintenanceService } from 'src/app/services/agent/agent-maintenance.service'; // Import AgentMaintenanceService
import { Agent } from '../../models/agent.model'; // Import Agent model

@Component({
  selector: 'app-plaintes',
  templateUrl: './plaintes.component.html',
  styleUrls: ['./plaintes.component.css']
})
export class PlaintesComponent implements OnInit {

  plainteForm: FormGroup;
  plaintes: Plainte[] = [];
  agents: Agent[] = []; // Add agents array for dropdown

  constructor(
    private fb: FormBuilder,
    private plainteService: PlainteService,
    private agentService: AgentMaintenanceService // Inject AgentMaintenanceService
  ) {
    this.plainteForm = this.fb.group({
      description: ['', [Validators.required, Validators.maxLength(500)]],
      agentId: [null, Validators.required],
      chambreId: [null, Validators.required],
      date: [null, Validators.required] // Champ date ajouté ici
    });
  }

  ngOnInit(): void {
    this.loadPlaintes();
    this.loadAgents(); // Load agents when the component is initialized
  }

  loadPlaintes(): void {
    this.plainteService.getAllPlaintes().subscribe(
      (data: Plainte[]) => {
        this.plaintes = data;
      },
      error => {
        console.error('Erreur lors de la récupération des plaintes:', error);
      }
    );
  }

  loadAgents(): void {
    this.agentService.getAllAgents().subscribe(
      (data: Agent[]) => {
        this.agents = data; // Populate agents array
      },
      error => {
        console.error('Erreur lors de la récupération des agents:', error);
      }
    );
  }

  submitPlainte(): void {
    if (this.plainteForm.invalid) {
      return;
    }

    const plainteData: Plainte = {
      ...this.plainteForm.value,
      cloturee: false, // La valeur de cloturee est false par défaut
      agent: { id: this.plainteForm.value.agentId },
      chambre: { id: this.plainteForm.value.chambreId }
    };

    this.plainteService.createPlainte(plainteData).subscribe(
      response => {
        console.log('Plainte soumise avec succès', response);
        this.resetForm();
        this.loadPlaintes();
      },
      error => {
        console.error('Erreur lors de la soumission de la plainte', error);
      }
    );
  }

  deletePlainte(id: number): void {
    this.plainteService.deletePlainte(id).subscribe(
      () => {
        console.log('Plainte supprimée avec succès');
        this.loadPlaintes(); // Recharger les plaintes après la suppression
      },
      error => {
        console.error('Erreur lors de la suppression de la plainte', error);
      }
    );
  }

  resetForm(): void {
    this.plainteForm.reset();
  }
}
