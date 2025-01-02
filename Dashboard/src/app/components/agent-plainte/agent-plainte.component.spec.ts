import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentPlainteComponent } from './agent-plainte.component';

describe('AgentPlainteComponent', () => {
  let component: AgentPlainteComponent;
  let fixture: ComponentFixture<AgentPlainteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgentPlainteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AgentPlainteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
