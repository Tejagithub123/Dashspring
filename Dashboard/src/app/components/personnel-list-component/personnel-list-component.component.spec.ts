import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonnelListComponent } from './personnel-list-component.component';

describe('PersonnelListComponentComponent', () => {
  let component: PersonnelListComponent;
  let fixture: ComponentFixture<PersonnelListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonnelListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonnelListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
