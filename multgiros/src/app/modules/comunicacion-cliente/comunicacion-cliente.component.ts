import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-comunicacion-cliente',
  templateUrl: './comunicacion-cliente.component.html',
  styleUrls: ['./comunicacion-cliente.component.css']
})
export class ComunicacionClienteComponent implements OnInit {
  comunicacion: any = {};
  comunicaciones: any = [];
  clientes: any = [];
  usuarios: any = [];
  ordenes: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarComunicaciones();
    this.cargarListasRelacionadas();
  }

  cargarListasRelacionadas() {
    // Cargar clientes
    this.http.get<any>(`${environment.apiUrl}/cliente/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.clientes = response);

    // Cargar usuarios
    this.http.get<any>(`${environment.apiUrl}/usuario/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.usuarios = response);

    // Cargar órdenes
    this.http.get<any>(`${environment.apiUrl}/orden/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.ordenes = response);
  }

  buscarComunicaciones() {
    this.buscarComunicacionesServicio().subscribe(
      (response: any) => this.comunicaciones = response
    )
  }

  buscarComunicacionesServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/comunicacioncliente/listar`)
      .pipe(catchError(e => of([])));
  }

  buscarPorCliente(idCliente: string) {
    if (!idCliente || idCliente.trim() === '') {
      this.buscarComunicaciones();
      return;
    }
    
    const id = Number(idCliente);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de cliente válido');
      return;
    }

    this.buscarPorClienteServicio(id).subscribe(
      (response: any) => this.comunicaciones = response
    )
  }

  buscarPorClienteServicio(idCliente: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/comunicacioncliente/buscarporcliente/${idCliente}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por usuario
  buscarPorUsuario(idUsuario: string) {
    if (!idUsuario || idUsuario.trim() === '') {
      this.buscarComunicaciones();
      return;
    }
    
    const id = Number(idUsuario);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de usuario válido');
      return;
    }

    this.buscarPorUsuarioServicio(id).subscribe(
      (response: any) => this.comunicaciones = response
    )
  }

  buscarPorUsuarioServicio(idUsuario: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/comunicacioncliente/buscarporusuario/${idUsuario}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por orden
  buscarPorOrden(idOrden: string) {
    if (!idOrden || idOrden.trim() === '') {
      this.buscarComunicaciones();
      return;
    }
    
    const id = Number(idOrden);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de orden válido');
      return;
    }

    this.buscarPorOrdenServicio(id).subscribe(
      (response: any) => this.comunicaciones = response
    )
  }

  buscarPorOrdenServicio(idOrden: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/comunicacioncliente/buscarpororden/${idOrden}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por tipo de comunicación
  buscarPorTipoComunicacion(tipo: string) {
    if (!tipo || tipo.trim() === '') {
      this.buscarComunicaciones();
      return;
    }

    this.buscarPorTipoComunicacionServicio(tipo).subscribe(
      (response: any) => this.comunicaciones = response
    )
  }

  buscarPorTipoComunicacionServicio(tipo: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/comunicacioncliente/buscarportipocomunicacion/${tipo}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar comunicaciones no resueltas
  buscarNoResueltas() {
    this.buscarPorEstadoResueltoServicio(false).subscribe(
      (response: any) => this.comunicaciones = response
    )
  }

  buscarPorEstadoResueltoServicio(estado: boolean): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/comunicacioncliente/buscarporestadoresuelto/${estado}`)
      .pipe(catchError(e => of([])));
  }

  crearComunicacion() {
    let formFormulario: any = document.getElementById("formComunicacion");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.convertirTiposComunicacion();
      
      this.crearComunicacionServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  convertirTiposComunicacion() {
    // Convertir IDs numéricos
    const camposNumericos = ['idCliente', 'idUsuario', 'idOrden'];
    camposNumericos.forEach(campo => {
      if (this.comunicacion[campo]) {
        this.comunicacion[campo] = Number(this.comunicacion[campo]);
      }
    });

    // Convertir booleanos
    if (typeof this.comunicacion.estadoResuelto === 'string') {
      this.comunicacion.estadoResuelto = this.comunicacion.estadoResuelto === 'true';
    }
  }

  crearComunicacionServicio() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(`${environment.apiUrl}/comunicacioncliente/insertar`, this.comunicacion, httpOptions)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizar(comunicacion: any) {
    if (comunicacion.error) {
      alert("Error al guardar la comunicación: " + comunicacion.message);
    } else {
      alert("Comunicación guardada exitosamente con el id: " + comunicacion.idComunicacion);
      this.buscarComunicaciones();
      this.comunicacion = {};
    }
  }

  Limpiar() {
    this.comunicacion = {};
  }

  modificar(c: any) {
    this.comunicacion = { ...c };
  }

  Eliminar(c: any) {
    if (confirm('¿Está seguro de que desea eliminar esta comunicación?')) {
      this.eliminarComunicacionServicio(c.idComunicacion).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarComunicacionServicio(idComunicacion: any) {
    return this.http.delete<any>(`${environment.apiUrl}/comunicacioncliente/eliminar/${idComunicacion}`)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar la comunicación: " + response.message);
    } else {
      alert("Comunicación eliminada exitosamente");
      this.buscarComunicaciones();
    }
  }

  // NUEVO MÉTODO: Marcar como resuelta
  marcarComoResuelta(comunicacion: any) {
    comunicacion.estadoResuelto = true;
    comunicacion.fechaResolucion = new Date();
    this.crearComunicacionServicio().subscribe(
      (response: any) => {
        if (!response.error) {
          alert("Comunicación marcada como resuelta");
          this.buscarComunicaciones();
        }
      }
    );
  }
}