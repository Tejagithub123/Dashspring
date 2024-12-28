import { TestBed } from '@angular/core/testing';

import { AgentMaintenanceService } from './agent-maintenance.service';

describe('AgentMaintenanceService', () => {
  let service: AgentMaintenanceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AgentMaintenanceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
