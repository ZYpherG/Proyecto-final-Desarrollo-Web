import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriaInventarioComponent } from './categoria-inventario.component';

describe('CategoriaInventarioComponent', () => {
  let component: CategoriaInventarioComponent;
  let fixture: ComponentFixture<CategoriaInventarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoriaInventarioComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoriaInventarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
