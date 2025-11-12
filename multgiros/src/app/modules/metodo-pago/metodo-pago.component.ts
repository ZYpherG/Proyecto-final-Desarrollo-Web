import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-metodo-pago',
  templateUrl: './metodo-pago.component.html',
  styleUrls: ['./metodo-pago.component.css']
})
export class MetodoPagoComponent implements OnInit {
  metodo: any = {};
  metodos: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarMetodos();
  }

  buscarMetodos() {
    this.buscarMetodosServicio().subscribe(
      (response: any) => this.metodos = response
    )
  }

  buscarMetodosServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/metodopago/listar`)
      .pipe(
        catchError(e => "error")
      );
  }

  buscarPorNombre(nombre: string) {
    this.buscarPorNombreServicio(nombre).subscribe(
      (response: any) => this.metodos = [response]
    )
  }

  buscarPorNombreServicio(nombre: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/metodopago/buscarpornombre/${nombre}`)
      .pipe(
        catchError(e => "error")
      );
  }

  crearMetodo() {
    let formFormulario: any = document.getElementById("formMetodo");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.crearMetodoServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  crearMetodoServicio() {
    var httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
    return this.http.post<any>(`${environment.apiUrl}/metodopago/insertar`, this.metodo, httpOptions)
      .pipe(
        catchError(e => "error")
      );
  }

  actualizar(metodo: any) {
    alert("Método de pago guardado exitosamente con el id: " + metodo.idMetodoPago);
    this.buscarMetodos();
    this.metodo = {};
  }

  Limpiar() {
    this.metodo = {};
  }

  modificar(m: any) {
    this.metodo = m;
  }

  Eliminar(m: any) {
    this.eliminarMetodoServicio(m.idMetodoPago).subscribe(
      (response: any) => this.actualizarEliminar(response)
    )
  }

  eliminarMetodoServicio(idMetodo: any) {
    return this.http.delete<any>(`${environment.apiUrl}/metodopago/eliminar/${idMetodo}`)
      .pipe(
        catchError(e => "error")
      );
  }

  actualizarEliminar(response: any) {
    alert("Método de pago eliminado exitosamente");
    this.buscarMetodos();
  }
}