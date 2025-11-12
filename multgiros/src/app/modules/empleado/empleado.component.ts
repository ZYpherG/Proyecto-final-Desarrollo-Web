import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-empleado',
  templateUrl: './empleado.component.html',
  styleUrls: ['./empleado.component.css']
})
export class EmpleadoComponent implements OnInit {
  empleado: any = {};
  empleados: any = [];
  personas: any = [];
  sucursales: any = [];
  estadosEmpleado: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarEmpleados();
    this.cargarListasRelacionadas();
  }

  cargarListasRelacionadas() {
    // Cargar personas
    this.http.get<any>(`${environment.apiUrl}/persona/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.personas = response);

    // Cargar sucursales
    this.http.get<any>(`${environment.apiUrl}/sucursal/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.sucursales = response);

    // Cargar estados de empleado
    this.http.get<any>(`${environment.apiUrl}/estadoempleado/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.estadosEmpleado = response);
  }

  buscarEmpleados() {
    this.buscarEmpleadosServicio().subscribe(
      (response: any) => this.empleados = response
    )
  }

  buscarEmpleadosServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/empleado/listar`)
      .pipe(catchError(e => of([])));
  }

  buscarPorCodigo(codigo: string) {
    if (!codigo || codigo.trim() === '') {
      this.buscarEmpleados();
      return;
    }

    this.buscarPorCodigoServicio(codigo).subscribe(
      (response: any) => this.empleados = response ? [response] : []
    )
  }

  buscarPorCodigoServicio(codigo: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/empleado/buscarporcodigo/${codigo}`)
      .pipe(catchError(e => of(null)));
  }

  // NUEVO MÉTODO: Buscar por persona
  buscarPorPersona(idPersona: string) {
    if (!idPersona || idPersona.trim() === '') {
      this.buscarEmpleados();
      return;
    }
    
    const id = Number(idPersona);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de persona válido');
      return;
    }

    this.buscarPorPersonaServicio(id).subscribe(
      (response: any) => this.empleados = response ? [response] : []
    )
  }

  buscarPorPersonaServicio(idPersona: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/empleado/buscarporpersona/${idPersona}`)
      .pipe(catchError(e => of(null)));
  }

  // NUEVO MÉTODO: Buscar por sucursal
  buscarPorSucursal(idSucursal: string) {
    if (!idSucursal || idSucursal.trim() === '') {
      this.buscarEmpleados();
      return;
    }
    
    const id = Number(idSucursal);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de sucursal válido');
      return;
    }

    this.buscarPorSucursalServicio(id).subscribe(
      (response: any) => this.empleados = response
    )
  }

  buscarPorSucursalServicio(idSucursal: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/empleado/buscarporSucursal/${idSucursal}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por estado de empleado
  buscarPorEstadoEmpleado(idEstado: string) {
    if (!idEstado || idEstado.trim() === '') {
      this.buscarEmpleados();
      return;
    }
    
    const id = Number(idEstado);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de estado válido');
      return;
    }

    this.buscarPorEstadoEmpleadoServicio(id).subscribe(
      (response: any) => this.empleados = response
    )
  }

  buscarPorEstadoEmpleadoServicio(idEstado: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/empleado/buscarporEstadoEmpleado/${idEstado}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar empleados activos
  buscarActivos() {
    this.buscarPorActivoServicio(true).subscribe(
      (response: any) => this.empleados = response
    )
  }

  buscarPorActivoServicio(activo: boolean): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/empleado/buscarporactivo/${activo}`)
      .pipe(catchError(e => of([])));
  }

  crearEmpleado() {
    let formFormulario: any = document.getElementById("formEmpleado");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.convertirTiposEmpleado();
      
      this.crearEmpleadoServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  convertirTiposEmpleado() {
    // Convertir IDs numéricos
    const camposNumericos = ['idPersona', 'idSucursal', 'idEstadoEmpleado'];
    camposNumericos.forEach(campo => {
      if (this.empleado[campo]) {
        this.empleado[campo] = Number(this.empleado[campo]);
      }
    });

    // Convertir valores decimales
    if (this.empleado.salarioBase) {
      this.empleado.salarioBase = Number(this.empleado.salarioBase);
    }

    // Convertir booleanos
    if (typeof this.empleado.activo === 'string') {
      this.empleado.activo = this.empleado.activo === 'true';
    }
  }

  crearEmpleadoServicio() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(`${environment.apiUrl}/empleado/insertar`, this.empleado, httpOptions)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizar(empleado: any) {
    if (empleado.error) {
      alert("Error al guardar el empleado: " + empleado.message);
    } else {
      alert("Empleado guardado exitosamente con el id: " + empleado.idEmpleado);
      this.buscarEmpleados();
      this.empleado = {};
    }
  }

  Limpiar() {
    this.empleado = {};
  }

  modificar(e: any) {
    this.empleado = { ...e };
  }

  Eliminar(e: any) {
    if (confirm('¿Está seguro de que desea eliminar este empleado?')) {
      this.eliminarEmpleadoServicio(e.idEmpleado).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarEmpleadoServicio(idEmpleado: any) {
    return this.http.delete<any>(`${environment.apiUrl}/empleado/eliminar/${idEmpleado}`)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar el empleado: " + response.message);
    } else {
      alert("Empleado eliminado exitosamente");
      this.buscarEmpleados();
    }
  }

  // NUEVO MÉTODO: Dar de baja empleado
  darDeBaja(empleado: any) {
    empleado.fechaEgreso = new Date().toISOString().split('T')[0];
    empleado.activo = false;
    this.crearEmpleadoServicio().subscribe(
      (response: any) => {
        if (!response.error) {
          alert("Empleado dado de baja exitosamente");
          this.buscarEmpleados();
        }
      }
    );
  }
}