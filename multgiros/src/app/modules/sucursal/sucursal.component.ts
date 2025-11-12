import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-sucursal',
  templateUrl: './sucursal.component.html',
  styleUrls: ['./sucursal.component.css']
})
export class SucursalComponent implements OnInit {
  sucursal: any = {};
  sucursales: any = [];
  girosNegocio: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarSucursales();
    this.cargarListasRelacionadas();
  }

  cargarListasRelacionadas() {
    // Cargar giros de negocio
    this.http.get<any>(`${environment.apiUrl}/gironegocio/listar`
)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.girosNegocio = response);
  }

  buscarSucursales() {
    this.buscarSucursalesServicio().subscribe(
      (response: any) => this.sucursales = response
    )
  }

  buscarSucursalesServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/sucursal/listar`)
      .pipe(catchError(e => of([])));
  }

  buscarPorCodigo(codigo: string) {
    if (!codigo || codigo.trim() === '') {
      this.buscarSucursales();
      return;
    }

    this.buscarPorCodigoServicio(codigo).subscribe(
      (response: any) => this.sucursales = response ? [response] : []
    )
  }

  buscarPorCodigoServicio(codigo: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/sucursal/buscarporcodigo/${codigo}`)
      .pipe(catchError(e => of(null)));
  }

  buscarPorGiro(idGiroNegocio: string) {
    if (!idGiroNegocio || idGiroNegocio.trim() === '') {
      this.buscarSucursales();
      return;
    }
    
    const id = Number(idGiroNegocio);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de giro válido');
      return;
    }

    this.buscarPorGiroServicio(id).subscribe(
      (response: any) => this.sucursales = response
    )
  }

  buscarPorGiroServicio(idGiroNegocio: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/sucursal/buscarporgiro/${idGiroNegocio}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por departamento
  buscarPorDepartamento(departamento: string) {
    if (!departamento || departamento.trim() === '') {
      this.buscarSucursales();
      return;
    }

    this.buscarPorDepartamentoServicio(departamento).subscribe(
      (response: any) => this.sucursales = response
    )
  }

  buscarPorDepartamentoServicio(departamento: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/sucursal/buscarporDepartamento/${departamento}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar sucursales activas
  buscarActivas() {
    this.buscarPorActivaServicio(true).subscribe(
      (response: any) => this.sucursales = response
    )
  }

  buscarPorActivaServicio(activa: boolean): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/sucursal/buscarporactiva/${activa}`)
      .pipe(catchError(e => of([])));
  }

  crearSucursal() {
    let formFormulario: any = document.getElementById("formSucursal");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.convertirTiposSucursal();
      
      this.crearSucursalServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  convertirTiposSucursal() {
    // Convertir IDs numéricos
    if (this.sucursal.idGiroNegocio) {
      this.sucursal.idGiroNegocio = Number(this.sucursal.idGiroNegocio);
    }

    // Convertir booleanos
    if (typeof this.sucursal.activa === 'string') {
      this.sucursal.activa = this.sucursal.activa === 'true';
    }
  }

  crearSucursalServicio() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(`${environment.apiUrl}/sucursal/insertar`, this.sucursal, httpOptions)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizar(sucursal: any) {
    if (sucursal.error) {
      alert("Error al guardar la sucursal: " + sucursal.message);
    } else {
      alert("Sucursal guardada exitosamente con el id: " + sucursal.idSucursal);
      this.buscarSucursales();
      this.sucursal = {};
    }
  }

  Limpiar() {
    this.sucursal = {};
  }

  modificar(s: any) {
    this.sucursal = { ...s };
  }

  Eliminar(s: any) {
    if (confirm('¿Está seguro de que desea eliminar esta sucursal?')) {
      this.eliminarSucursalServicio(s.idSucursal).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarSucursalServicio(idSucursal: any) {
    return this.http.delete<any>(`${environment.apiUrl}/sucursal/eliminar/${idSucursal}` + idSucursal)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar la sucursal: " + response.message);
    } else {
      alert("Sucursal eliminada exitosamente");
      this.buscarSucursales();
    }
  }

  // NUEVO MÉTODO: Cerrar sucursal
  cerrarSucursal(sucursal: any) {
    sucursal.fechaCierre = new Date().toISOString().split('T')[0];
    sucursal.activa = false;
    this.crearSucursalServicio().subscribe(
      (response: any) => {
        if (!response.error) {
          alert("Sucursal cerrada exitosamente");
          this.buscarSucursales();
        }
      }
    );
  }

  // NUEVO MÉTODO: Reabrir sucursal
  reabrirSucursal(sucursal: any) {
    sucursal.fechaCierre = null;
    sucursal.activa = true;
    this.crearSucursalServicio().subscribe(
      (response: any) => {
        if (!response.error) {
          alert("Sucursal reabierta exitosamente");
          this.buscarSucursales();
        }
      }
    );
  }

  // NUEVO MÉTODO: Obtener clase CSS según estado
  obtenerClaseEstado(activa: boolean): string {
    return activa ? 'badge bg-success' : 'badge bg-danger';
  }

  // NUEVO MÉTODO: Obtener texto según estado
  obtenerTextoEstado(activa: boolean): string {
    return activa ? 'Activa' : 'Inactiva';
  }
}