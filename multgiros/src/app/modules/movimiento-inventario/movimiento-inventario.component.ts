import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-movimiento-inventario',
  templateUrl: './movimiento-inventario.component.html',
  styleUrls: ['./movimiento-inventario.component.css']
})
export class MovimientoInventarioComponent implements OnInit {
  movimiento: any = {};
  movimientos: any = [];
  inventarios: any = [];
  ordenes: any = [];
  usuarios: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarMovimientos();
    this.cargarListasRelacionadas();
  }

  cargarListasRelacionadas() {
    // Cargar inventarios
    this.http.get<any>(`${environment.apiUrl}/inventario/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.inventarios = response);

    // Cargar órdenes
    this.http.get<any>(`${environment.apiUrl}/orden/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.ordenes = response);

    // Cargar usuarios
    this.http.get<any>(`${environment.apiUrl}/usuario/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.usuarios = response);
  }

  buscarMovimientos() {
    this.buscarMovimientosServicio().subscribe(
      (response: any) => this.movimientos = response
    )
  }

  buscarMovimientosServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/movimientoinventario/listar`)
      .pipe(catchError(e => of([])));
  }

  buscarPorInventario(idInventario: string) {
    if (!idInventario || idInventario.trim() === '') {
      this.buscarMovimientos();
      return;
    }
    
    const id = Number(idInventario);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de inventario válido');
      return;
    }

    this.buscarPorInventarioServicio(id).subscribe(
      (response: any) => this.movimientos = response
    )
  }

  buscarPorInventarioServicio(idInventario: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/movimientoinventario/buscarporinventario/${idInventario}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por tipo de movimiento
  buscarPorTipoMovimiento(tipo: string) {
    if (!tipo || tipo.trim() === '') {
      this.buscarMovimientos();
      return;
    }

    this.buscarPorTipoMovimientoServicio(tipo).subscribe(
      (response: any) => this.movimientos = response
    )
  }

  buscarPorTipoMovimientoServicio(tipo: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/movimientoinventario/buscarportipomovimiento/${tipo}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por orden
  buscarPorOrden(idOrden: string) {
    if (!idOrden || idOrden.trim() === '') {
      this.buscarMovimientos();
      return;
    }
    
    const id = Number(idOrden);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de orden válido');
      return;
    }

    this.buscarPorOrdenServicio(id).subscribe(
      (response: any) => this.movimientos = response
    )
  }

  buscarPorOrdenServicio(idOrden: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/movimientoinventario/buscarpororden/${idOrden}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por usuario
  buscarPorUsuario(idUsuario: string) {
    if (!idUsuario || idUsuario.trim() === '') {
      this.buscarMovimientos();
      return;
    }
    
    const id = Number(idUsuario);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de usuario válido');
      return;
    }

    this.buscarPorUsuarioServicio(id).subscribe(
      (response: any) => this.movimientos = response
    )
  }

  buscarPorUsuarioServicio(idUsuario: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/movimientoinventario/buscarporusuario/${idUsuario}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por rango de fechas
  buscarPorRangoFechas(fechaInicio: string, fechaFin: string) {
    if (!fechaInicio || !fechaFin) {
      alert('Por favor ingrese ambas fechas');
      return;
    }

    this.buscarPorRangoFechasServicio(fechaInicio, fechaFin).subscribe(
      (response: any) => this.movimientos = response
    )
  }

  buscarPorRangoFechasServicio(fechaInicio: string, fechaFin: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/movimientoinventario/buscarporfecha?inicio=${fechaInicio}&fin=${fechaFin}`)
      .pipe(catchError(e => of([])));
  }

  crearMovimiento() {
    let formFormulario: any = document.getElementById("formMovimiento");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.convertirTiposMovimiento();
      
      this.crearMovimientoServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  convertirTiposMovimiento() {
    // Convertir IDs numéricos
    const camposNumericos = ['idInventario', 'idOrden', 'idUsuario'];
    camposNumericos.forEach(campo => {
      if (this.movimiento[campo]) {
        this.movimiento[campo] = Number(this.movimiento[campo]);
      }
    });

    // Convertir valores decimales
    const camposDecimales = ['cantidad', 'cantidadAnterior', 'cantidadNueva'];
    camposDecimales.forEach(campo => {
      if (this.movimiento[campo]) {
        this.movimiento[campo] = Number(this.movimiento[campo]);
      }
    });
  }

  crearMovimientoServicio() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(`${environment.apiUrl}/movimientoinventario/insertar`, this.movimiento, httpOptions)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizar(movimiento: any) {
    if (movimiento.error) {
      alert("Error al guardar el movimiento: " + movimiento.message);
    } else {
      alert("Movimiento de inventario guardado exitosamente con el id: " + movimiento.idMovimiento);
      this.buscarMovimientos();
      this.movimiento = {};
    }
  }

  Limpiar() {
    this.movimiento = {};
  }

  modificar(m: any) {
    this.movimiento = { ...m };
  }

  Eliminar(m: any) {
    if (confirm('¿Está seguro de que desea eliminar este movimiento?')) {
      this.eliminarMovimientoServicio(m.idMovimiento).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarMovimientoServicio(idMovimiento: any) {
    return this.http.delete<any>(`${environment.apiUrl}/movimientoinventario/eliminar/${idMovimiento}`)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar el movimiento: " + response.message);
    } else {
      alert("Movimiento de inventario eliminado exitosamente");
      this.buscarMovimientos();
    }
  }

  // NUEVO MÉTODO: Calcular stock después del movimiento
  calcularStock(movimiento: any): string {
    if (movimiento.cantidadAnterior !== undefined && movimiento.cantidadNueva !== undefined) {
      return `${movimiento.cantidadAnterior} → ${movimiento.cantidadNueva}`;
    }
    return 'N/A';
  }

  // NUEVO MÉTODO: Determinar clase CSS según tipo de movimiento
  obtenerClaseTipoMovimiento(tipo: string): string {
    switch (tipo) {
      case 'ENTRADA': return 'text-success';
      case 'SALIDA': return 'text-danger';
      case 'AJUSTE': return 'text-warning';
      case 'DEVOLUCION': return 'text-info';
      case 'PERDIDA': return 'text-danger';
      default: return 'text-secondary';
    }
  }

  // NUEVO MÉTODO: Obtener icono según tipo de movimiento
  obtenerIconoTipoMovimiento(tipo: string): string {
    switch (tipo) {
      case 'ENTRADA': return '↑';
      case 'SALIDA': return '↓';
      case 'AJUSTE': return '↔';
      case 'DEVOLUCION': return '↩';
      case 'PERDIDA': return '⚠';
      default: return '•';
    }
  }
}