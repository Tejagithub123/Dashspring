import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Agent } from 'src/app/models/agent.model';
import { AuthService } from '../auth/auth.service'; // Import AuthService

@Injectable({
  providedIn: 'root',
})
export class AgentMaintenanceService {
  private baseUrl = 'http://localhost:8090/admin/agent'; // Backend API URL

  constructor(private http: HttpClient, private authService: AuthService) {}

  // Method to get the Authorization header with Bearer token
  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Get token from AuthService
    console.log('Token:', token); // Log token for debugging (remove in production)
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  }

  // Add agent
  addAgent(agent: Agent): Observable<Agent> {
    return this.http.post<Agent>(this.baseUrl, agent, { headers: this.getHeaders() });
  }

  // Get all agents
  getAllAgents(): Observable<Agent[]> {
    return this.http.get<Agent[]>(`${this.baseUrl}/agentAll`, { headers: this.getHeaders() });
  }

  // Get agent by ID
  getAgentById(id: number): Observable<Agent> {
    return this.http.get<Agent>(`${this.baseUrl}/${id}`, { headers: this.getHeaders() });
  }

  // Update agent by ID
  updateAgent(id: number, agent: Agent): Observable<Agent> {
    return this.http.put<Agent>(`${this.baseUrl}/${id}`, agent, { headers: this.getHeaders() });
  }

  // Delete agent by ID
  deleteAgent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { headers: this.getHeaders() });
  }

  getAllAgentsPersonnels(): Observable<Agent[]> {
    const personnelUrl = 'http://localhost:8090/personnel/agent/agentAll';
    return this.http.get<Agent[]>(personnelUrl, { headers: this.getHeaders() });
  }
  
}
