import { Component, OnInit } from '@angular/core';
import { PlainteService } from 'src/app/services/plainte/plainte.service';
import { Plainte } from 'src/app/models/plainte.model';

@Component({
  selector: 'app-agent-plainte',
  templateUrl: './agent-plainte.component.html',
  styleUrls: ['./agent-plainte.component.scss']
})
export class AgentPlainteComponent implements OnInit {
  plaintes: Plainte[] = [];
  filteredPlaintes: Plainte[] = [];
  agentId: number | undefined;

  constructor(private plainteService: PlainteService) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      this.agentId = payload.id;
    }

    if (this.agentId) {
      this.loadPlainteByAgent(this.agentId);
    }
  }

  loadPlainteByAgent(agentId: number): void {
    this.plainteService.getAllPlaintesAgent().subscribe(
      (plaintes) => {
        this.plaintes = plaintes;
        this.filteredPlaintes = this.plaintes.filter(
          (plainte) => plainte.agent.id === agentId
        );
      },
      (error) => console.error('Erreur lors du chargement des plaintes:', error)
    );
  }

  cloturerPlainte(id: number): void {
    this.plainteService.cloturerPlainte(id).subscribe(
      (updatedPlainte) => {
        // Update plaintes array
        const allIndex = this.plaintes.findIndex((p) => p.id === id);
        if (allIndex !== -1) {
          this.plaintes[allIndex] = updatedPlainte;
        }
  
        // Update filteredPlaintes array
        const filteredIndex = this.filteredPlaintes.findIndex((p) => p.id === id);
        if (filteredIndex !== -1) {
          this.filteredPlaintes[filteredIndex] = updatedPlainte;
        }
      },
      (error) =>
        console.error('Erreur lors de la clôture de la plainte:', error)
    );
  }
  
}
