import { Component, OnInit } from '@angular/core';
import { AgentMaintenanceService } from 'src/app/services/agent/agent-maintenance.service';
import { Agent } from '../../models/agent.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-agent',
  templateUrl: './add-agent.component.html',
  styleUrls: ['./add-agent.component.css'],
})
export class AgentMaintenanceComponent implements OnInit {
  agent: Agent = {
    nom: '',
    prenom: '',
    email: '',
    mdp: '',
    cin: 0,
    specialite: '',
    dateNaissance:''
  };

  constructor(
    private agentService: AgentMaintenanceService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // You can add logic to fetch and edit an existing agent if needed
  }

  saveAgent(): void {
    console.log('Saving agent:', this.agent);

    this.agentService.addAgent(this.agent).subscribe(
      (response) => {
        console.log('Agent saved successfully:', response);
        alert('Agent saved successfully!');
        this.router.navigate(['/liste-agents']); // Navigate to the agents list page
      },
      (error) => {
        console.error('Error saving agent:', error);
        alert('Failed to save the agent.');
      }
    );
  }
}
