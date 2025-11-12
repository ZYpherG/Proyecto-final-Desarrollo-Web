import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentoAsociadoComponent } from './documento-asociado.component';

describe('DocumentoAsociadoComponent', () => {
  let component: DocumentoAsociadoComponent;
  let fixture: ComponentFixture<DocumentoAsociadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DocumentoAsociadoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentoAsociadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
