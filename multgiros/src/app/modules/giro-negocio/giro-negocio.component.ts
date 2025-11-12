import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-giro-negocio',
  templateUrl: './giro-negocio.component.html',
  styleUrls: ['./giro-negocio.component.css']
})
export class GiroNegocioComponent implements OnInit {
  giro: any = {};
  giros: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarGiros();
  }

  buscarGiros() {
    this.buscarGirosServicio().subscribe(
      (response: any) => this.giros = response
    )
  }

  buscarGirosServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/gironegocio/listar`)
      .pipe(
        catchError(e => "error")
      );
  }

  buscarPorCodigo(codigo: string) {
    this.buscarPorCodigoServicio(codigo).subscribe(
      (response: any) => this.giros = [response]
    )
  }

  buscarPorCodigoServicio(codigo: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/gironegocio/buscarporcodigo/${codigo}`)
      .pipe(
        catchError(e => "error")
      );
  }

  crearGiro() {
    let formFormulario: any = document.getElementById("formGiro");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.crearGiroServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  crearGiroServicio() {
    var httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
    return this.http.post<any>(`${environment.apiUrl}/gironegocio/insertar`, this.giro, httpOptions)
      .pipe(
        catchError(e => "error")
      );
  }

  actualizar(giro: any) {
    alert("Giro de negocio guardado exitosamente con el id: " + giro.idGiroNegocio);
    this.buscarGiros();
    this.giro = {};
  }

  Limpiar() {
    this.giro = {};
  }

  modificar(g: any) {
    this.giro = g;
  }

  Eliminar(g: any) {
    this.eliminarGiroServicio(g.idGiroNegocio).subscribe(
      (response: any) => this.actualizarEliminar(response)
    )
  }

  eliminarGiroServicio(idGiro: any) {
    return this.http.delete<any>(`${environment.apiUrl}/gironegocio/eliminar/${idGiro}`)
      .pipe(
        catchError(e => "error")
      );
  }

  actualizarEliminar(response: any) {
    alert("Giro de negocio eliminado exitosamente");
    this.buscarGiros();
  }
}