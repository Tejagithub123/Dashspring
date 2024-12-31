import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaintesListComponent } from './plaintes-list.component';

describe('PlaintesListComponent', () => {
  let component: PlaintesListComponent;
  let fixture: ComponentFixture<PlaintesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlaintesListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaintesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
