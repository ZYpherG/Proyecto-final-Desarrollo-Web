import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GiroNegocioComponent } from './giro-negocio.component';

describe('GiroNegocioComponent', () => {
  let component: GiroNegocioComponent;
  let fixture: ComponentFixture<GiroNegocioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GiroNegocioComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GiroNegocioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
