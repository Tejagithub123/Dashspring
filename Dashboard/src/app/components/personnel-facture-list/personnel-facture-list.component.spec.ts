import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonnelFactureListComponent } from './personnel-facture-list.component';

describe('PersonnelFactureListComponent', () => {
  let component: PersonnelFactureListComponent;
  let fixture: ComponentFixture<PersonnelFactureListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonnelFactureListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonnelFactureListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
