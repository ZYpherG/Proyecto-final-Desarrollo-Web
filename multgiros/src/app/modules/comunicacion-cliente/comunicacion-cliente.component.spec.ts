import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComunicacionClienteComponent } from './comunicacion-cliente.component';

describe('ComunicacionClienteComponent', () => {
  let component: ComunicacionClienteComponent;
  let fixture: ComponentFixture<ComunicacionClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ComunicacionClienteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComunicacionClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
