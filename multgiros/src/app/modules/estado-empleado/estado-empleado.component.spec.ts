import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadoEmpleadoComponent } from './estado-empleado.component';

describe('EstadoEmpleadoComponent', () => {
  let component: EstadoEmpleadoComponent;
  let fixture: ComponentFixture<EstadoEmpleadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EstadoEmpleadoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EstadoEmpleadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
