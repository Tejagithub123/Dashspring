import { Component, OnInit } from '@angular/core';
import { PlainteService } from '../../services/plainte/plainte.service';
import { ChambreService } from '../../services/chambre/chambre.service'; 
import { Plainte } from '../../models/plainte.model';
import { Agent } from '../../models/agent.model'; 
import { Chambre } from '../../models/chambre.model'; 
import { AgentMaintenanceService } from 'src/app/services/agent/agent-maintenance.service';
@Component({
  selector: 'app-plaintes-list',
  templateUrl: './plaintes-list.component.html',
  styleUrls: ['./plaintes-list.component.css']
})
export class PlaintesListComponent implements OnInit {

  plaintes: Plainte[] = [];
  agents: Agent[] = [];
  chambres: Chambre[] = [];

  constructor(
    private plainteService: PlainteService,
    private chambreService: ChambreService ,
    private agentMaintenanceService : AgentMaintenanceService
  ) { }

  ngOnInit(): void {
    this.loadPlaintes();
    this.loadAgents();
    this.loadChambres();
  }

  
  loadPlaintes(): void {
    this.plainteService.getAllPlaintes().subscribe(
      (data: Plainte[]) => {
        this.plaintes = data;
        this.matchPlaintesWithAgents(); 
        this.matchPlaintesWithChambres(); 
      },
      error => {
        console.error('Erreur lors de la récupération des plaintes:', error);
      }
    );
  }

  
  loadAgents(): void {
    this.agentMaintenanceService.getAllAgents().subscribe(
      (data: Agent[]) => {
        this.agents = data;
      },
      error => {
        console.error('Erreur lors de la récupération des agents:', error);
      }
    );
  }

  
  loadChambres(): void {
    this.chambreService.getListeChambres().subscribe(
      (data: Chambre[]) => {
        this.chambres = data;
      },
      error => {
        console.error('Erreur lors de la récupération des chambres:', error);
      }
    );
  }

  
  matchPlaintesWithAgents(): void {
    for (let agent of this.agents) {
      
      agent.plaintes = this.plaintes.filter(plainte => plainte.id === agent.id);
    }
  }

  
  matchPlaintesWithChambres(): void {
    for (let chambre of this.chambres) {
      
      chambre.plaintes = this.plaintes.filter(plainte => plainte.id === chambre.id);
    }
  }

  
  deletePlainte(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette plainte ?')) {
      this.plainteService.deletePlainte(id).subscribe(
        () => {
          console.log('Plainte supprimée avec succès');
          this.loadPlaintes(); 
        },
        error => {
          console.error('Erreur lors de la suppression de la plainte', error);
        }
      );
    }
  }   


  
  isPlainteAssignedToAgent(agent: Agent, plainte: Plainte): boolean {
    return agent.plaintes?.some(p => p.id === plainte.id) ?? false;
  }
  
  isPlainteAssignedToChambre(chambre: Chambre, plainte: Plainte): boolean {
    return chambre.plaintes?.some(p => p.id === plainte.id) ?? false;
  }

}
