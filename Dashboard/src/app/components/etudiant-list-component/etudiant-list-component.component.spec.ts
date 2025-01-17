import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EtudiantListComponent } from './etudiant-list-component.component';

describe('EtudiantListComponent', () => {
  let component: EtudiantListComponent;
  let fixture: ComponentFixture<EtudiantListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EtudiantListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EtudiantListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
