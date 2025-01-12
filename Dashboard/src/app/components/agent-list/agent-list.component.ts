import { Component, OnInit } from '@angular/core';
import { AgentMaintenanceService } from 'src/app/services/agent/agent-maintenance.service';
import { Agent } from 'src/app/models/agent.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-agent-list',
  templateUrl: './agent-list.component.html',
  styleUrls: ['./agent-list.component.scss'],
})
export class AgentListComponent implements OnInit {
  agents: Agent[] = [];
  selectedAgent!: Agent;
  originalAgent!: Agent;

  constructor(
    private agentService: AgentMaintenanceService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.loadAgents();
  }

  loadAgents(): void {
    this.agentService.getAllAgents().subscribe((agents) => {
      this.agents = agents;
    });
  }

  openUpdateModal(content: any, agent: Agent): void {
    this.originalAgent = { ...agent };
    this.selectedAgent = { ...agent };
    this.modalService.open(content, { size: 'lg' });
  }

  updateAgent(): void {
    if (this.selectedAgent.id) {
      const updatePayload: any = {};

      if (this.selectedAgent.nom !== this.originalAgent.nom) {
        updatePayload.nom = this.selectedAgent.nom;
      }

      if (this.selectedAgent.prenom !== this.originalAgent.prenom) {
        updatePayload.prenom = this.selectedAgent.prenom;
      }

      if (this.selectedAgent.email !== this.originalAgent.email) {
        updatePayload.email = this.selectedAgent.email;
      }

      if (this.selectedAgent.mdp !== this.originalAgent.mdp) {
        updatePayload.mdp = this.selectedAgent.mdp;
      }

   // Check if the speciality has changed
   if (this.selectedAgent.specialite !== this.originalAgent.specialite) {
    updatePayload.specialite = this.selectedAgent.specialite;
  }

      if (this.selectedAgent.cin !== this.originalAgent.cin) {
        updatePayload.cin = this.selectedAgent.cin;
      }
      if (this.selectedAgent.dateNaissance !== this.originalAgent.dateNaissance) {
        updatePayload.dateNaissance = this.selectedAgent.dateNaissance;
      }
      

      this.agentService.updateAgent(this.selectedAgent.id, updatePayload).subscribe(
        () => {
          alert('Agent updated successfully!');
          this.loadAgents();
        },
        (error) => {
          console.error('Update failed:', error);
          alert('Failed to update agent');
        }
      );
    }
  }

  deleteAgent(id: number): void {
    if (confirm('Are you sure you want to delete this agent?')) {
      this.agentService.deleteAgent(id).subscribe(() => {
        alert('Agent deleted successfully!');
        this.loadAgents();
      });
    }
  }
}
