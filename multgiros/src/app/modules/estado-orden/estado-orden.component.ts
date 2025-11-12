import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-estado-orden',
  templateUrl: './estado-orden.component.html',
  styleUrls: ['./estado-orden.component.css']
})
export class EstadoOrdenComponent implements OnInit {
  estado: any = {};
  estados: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarEstados();
  }

  buscarEstados() {
    this.buscarEstadosServicio().subscribe(
      (response: any) => this.estados = response
    )
  }

  buscarEstadosServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/estadoorden/listar`)
      .pipe(
        catchError(e => "error")
      );
  }

  buscarPorNombre(nombre: string) {
    this.buscarPorNombreServicio(nombre).subscribe(
      (response: any) => this.estados = [response]
    )
  }

  buscarPorNombreServicio(nombre: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/estadoorden/buscarpornombre/${nombre}`)
      .pipe(
        catchError(e => "error")
      );
  }

  crearEstado() {
    let formFormulario: any = document.getElementById("formEstado");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.crearEstadoServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  crearEstadoServicio() {
    var httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
    return this.http.post<any>(`${environment.apiUrl}/estadoorden/insertar`, this.estado, httpOptions)
      .pipe(
        catchError(e => "error")
      );
  }

  actualizar(estado: any) {
    alert("Estado guardado exitosamente con el id: " + estado.idEstadoOrden);
    this.buscarEstados();
    this.estado = {};
  }

  Limpiar() {
    this.estado = {};
  }

  modificar(e: any) {
    this.estado = e;
  }

  Eliminar(e: any) {
    this.eliminarEstadoServicio(e.idEstadoOrden).subscribe(
      (response: any) => this.actualizarEliminar(response)
    )
  }

  eliminarEstadoServicio(idEstado: any) {
    return this.http.delete<any>(`${environment.apiUrl}/estadoorden/eliminar/${idEstado}`)
      .pipe(
        catchError(e => "error")
      );
  }

  actualizarEliminar(response: any) {
    alert("Estado eliminado exitosamente");
    this.buscarEstados();
  }
}