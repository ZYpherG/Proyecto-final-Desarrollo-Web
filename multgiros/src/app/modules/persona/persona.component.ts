import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-persona',
  templateUrl: './persona.component.html',
  styleUrls: ['./persona.component.css']
})
export class PersonaComponent implements OnInit {
  persona: any = {};
  personas: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarPersonas();
  }

  buscarPersonas() {
    this.buscarPersonasServicio().subscribe(
      (response: any) => this.personas = response
    )
  }

  buscarPersonasServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/persona/listar`)
      .pipe(
        catchError(e => "error")
      );
  }

  buscarPorDocumento(documento: string) {
    this.buscarPorDocumentoServicio(documento).subscribe(
      (response: any) => this.personas = [response]
    )
  }

  buscarPorDocumentoServicio(documento: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/persona/buscarpordocumento/${documento}`)
      .pipe(
        catchError(e => "error")
      );
  }

  crearPersona() {
    let formFormulario: any = document.getElementById("formPersona");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.crearPersonaServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  crearPersonaServicio() {
    var httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
    return this.http.post<any>(`${environment.apiUrl}/persona/insertar`, this.persona, httpOptions)
      .pipe(
        catchError(e => "error")
      );
  }

  actualizar(persona: any) {
    alert("Persona guardada exitosamente con el id: " + persona.idPersona);
    this.buscarPersonas();
    this.persona = {};
  }

  Limpiar() {
    this.persona = {};
  }

  modificar(p: any) {
    this.persona = p;
  }

  Eliminar(p: any) {
    this.eliminarPersonaServicio(p.idPersona).subscribe(
      (response: any) => this.actualizarEliminar(response)
    )
  }

  eliminarPersonaServicio(idPersona: any) {
    return this.http.delete<any>(`${environment.apiUrl}/persona/eliminar/${idPersona}`)
      .pipe(
        catchError(e => "error")
      );
  }

  actualizarEliminar(response: any) {
    alert("Persona eliminada exitosamente");
    this.buscarPersonas();
  }
}