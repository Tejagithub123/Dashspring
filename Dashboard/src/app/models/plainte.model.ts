import { Agent } from "./agent.model";

export interface Plainte {
  id?: number;
  date?: string;
  cloturee: boolean;
  description: string;
  chambre: {
    id: number;
  };
  agent : Agent 
}
