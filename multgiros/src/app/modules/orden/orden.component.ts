import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-orden',
  templateUrl: './orden.component.html',
  styleUrls: ['./orden.component.css']
})
export class OrdenComponent implements OnInit {
  orden: any = {};
  ordenes: any = [];
  girosNegocio: any = [];
  sucursales: any = [];
  clientes: any = [];
  proveedores: any = [];
  estadosOrden: any = [];
  empleados: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarOrdenes();
    this.cargarListasRelacionadas();
  }

  cargarListasRelacionadas() {
    // Cargar giros de negocio
    this.http.get<any>(`${environment.apiUrl}/gironegocio/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.girosNegocio = response);

    // Cargar sucursales
    this.http.get<any>(`${environment.apiUrl}/sucursal/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.sucursales = response);

    // Cargar clientes
    this.http.get<any>(`${environment.apiUrl}/cliente/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.clientes = response);

    // Cargar proveedores
    this.http.get<any>(`${environment.apiUrl}/proveedor/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.proveedores = response);

    // Cargar estados de orden
    this.http.get<any>(`${environment.apiUrl}/estadoorden/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.estadosOrden = response);

    // Cargar empleados
    this.http.get<any>(`${environment.apiUrl}/empleado/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.empleados = response);
  }

  buscarOrdenes() {
    this.buscarOrdenesServicio().subscribe(
      (response: any) => this.ordenes = response
    )
  }

  buscarOrdenesServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/orden/listar`)
      .pipe(catchError(e => of([])));
  }

  buscarPorNumero(numero: string) {
    if (!numero || numero.trim() === '') {
      this.buscarOrdenes();
      return;
    }

    this.buscarPorNumeroServicio(numero).subscribe(
      (response: any) => this.ordenes = response ? [response] : []
    )
  }

  buscarPorNumeroServicio(numero: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/orden/buscarpornumero/${numero}`)
      .pipe(catchError(e => of(null)));
  }

  // NUEVO MÉTODO: Buscar por tipo de orden
  buscarPorTipoOrden(tipo: string) {
    if (!tipo || tipo.trim() === '') {
      this.buscarOrdenes();
      return;
    }

    this.buscarPorTipoOrdenServicio(tipo).subscribe(
      (response: any) => this.ordenes = response
    )
  }

  buscarPorTipoOrdenServicio(tipo: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/orden/buscarportipoorden/${tipo}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por estado de orden
  buscarPorEstadoOrden(idEstado: string) {
    if (!idEstado || idEstado.trim() === '') {
      this.buscarOrdenes();
      return;
    }
    
    const id = Number(idEstado);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de estado válido');
      return;
    }

    this.buscarPorEstadoOrdenServicio(id).subscribe(
      (response: any) => this.ordenes = response
    )
  }

  buscarPorEstadoOrdenServicio(idEstado: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/orden/buscarporEstadoOrden/${idEstado}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por cliente
  buscarPorCliente(idCliente: string) {
    if (!idCliente || idCliente.trim() === '') {
      this.buscarOrdenes();
      return;
    }
    
    const id = Number(idCliente);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de cliente válido');
      return;
    }

    this.buscarPorClienteServicio(id).subscribe(
      (response: any) => this.ordenes = response
    )
  }

  buscarPorClienteServicio(idCliente: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/orden/buscarporCliente/${idCliente}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por proveedor
  buscarPorProveedor(idProveedor: string) {
    if (!idProveedor || idProveedor.trim() === '') {
      this.buscarOrdenes();
      return;
    }
    
    const id = Number(idProveedor);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de proveedor válido');
      return;
    }

    this.buscarPorProveedorServicio(id).subscribe(
      (response: any) => this.ordenes = response
    )
  }

  buscarPorProveedorServicio(idProveedor: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/orden/buscarporProveedor/${idProveedor}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por prioridad
  buscarPorPrioridad(prioridad: string) {
    if (!prioridad || prioridad.trim() === '') {
      this.buscarOrdenes();
      return;
    }

    this.buscarPorPrioridadServicio(prioridad).subscribe(
      (response: any) => this.ordenes = response
    )
  }

  buscarPorPrioridadServicio(prioridad: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/orden/buscarporPrioridad/${prioridad}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por rango de fechas
  buscarPorRangoFechas(fechaInicio: string, fechaFin: string) {
    if (!fechaInicio || !fechaFin) {
      alert('Por favor ingrese ambas fechas');
      return;
    }

    this.buscarPorRangoFechasServicio(fechaInicio, fechaFin).subscribe(
      (response: any) => this.ordenes = response
    )
  }

  buscarPorRangoFechasServicio(fechaInicio: string, fechaFin: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/orden/buscarporfecha?inicio=${fechaInicio}&fin=${fechaFin}`)
      .pipe(catchError(e => of([])));
  }

  crearOrden() {
    let formFormulario: any = document.getElementById("formOrden");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.convertirTiposOrden();
      
      this.crearOrdenServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  convertirTiposOrden() {
    // Convertir IDs numéricos
    const camposNumericos = [
      'idGiroNegocioOrigen', 'idGiroNegocioDestino', 'idGiroNegocioEjecutor',
      'idSucursalOrigen', 'idSucursalDestino', 'idCliente', 'idProveedor',
      'idEstadoOrden', 'idEmpleadoResponsable'
    ];
    camposNumericos.forEach(campo => {
      if (this.orden[campo]) {
        this.orden[campo] = Number(this.orden[campo]);
      }
    });

    // Convertir valores decimales
    const camposDecimales = ['subtotal', 'impuestos', 'descuentos', 'total'];
    camposDecimales.forEach(campo => {
      if (this.orden[campo]) {
        this.orden[campo] = Number(this.orden[campo]);
      }
    });

    // Convertir valores enteros
    const camposEnteros = ['duracionEstimada', 'duracionReal', 'porcentajeAvance'];
    camposEnteros.forEach(campo => {
      if (this.orden[campo]) {
        this.orden[campo] = Number(this.orden[campo]);
      }
    });
  }

  crearOrdenServicio() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(`${environment.apiUrl}/orden/insertar`, this.orden, httpOptions)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizar(orden: any) {
    if (orden.error) {
      alert("Error al guardar la orden: " + orden.message);
    } else {
      alert("Orden guardada exitosamente con el id: " + orden.idOrden);
      this.buscarOrdenes();
      this.orden = {};
    }
  }

  Limpiar() {
    this.orden = {};
  }

  modificar(o: any) {
    this.orden = { ...o };
  }

  Eliminar(o: any) {
    if (confirm('¿Está seguro de que desea eliminar esta orden?')) {
      this.eliminarOrdenServicio(o.idOrden).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarOrdenServicio(idOrden: any) {
    return this.http.delete<any>(`${environment.apiUrl}/orden/eliminar/${idOrden}`)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar la orden: " + response.message);
    } else {
      alert("Orden eliminada exitosamente");
      this.buscarOrdenes();
    }
  }

  // NUEVO MÉTODO: Calcular total automáticamente
  calcularTotal() {
    const subtotal = Number(this.orden.subtotal) || 0;
    const impuestos = Number(this.orden.impuestos) || 0;
    const descuentos = Number(this.orden.descuentos) || 0;
    
    this.orden.total = subtotal + impuestos - descuentos;
  }

  // NUEVO MÉTODO: Actualizar porcentaje de avance
  actualizarAvance(orden: any, porcentaje: number) {
    orden.porcentajeAvance = porcentaje;
    this.crearOrdenServicio().subscribe(
      (response: any) => {
        if (!response.error) {
          alert("Avance actualizado exitosamente");
          this.buscarOrdenes();
        }
      }
    );
  }

  // NUEVO MÉTODO: Marcar como completada
  marcarComoCompletada(orden: any) {
    orden.porcentajeAvance = 100;
    orden.fechaEntrega = new Date().toISOString().split('T')[0];
    this.crearOrdenServicio().subscribe(
      (response: any) => {
        if (!response.error) {
          alert("Orden marcada como completada");
          this.buscarOrdenes();
        }
      }
    );
  }

  // NUEVO MÉTODO: Obtener clase CSS según prioridad
  obtenerClasePrioridad(prioridad: string): string {
    switch (prioridad) {
      case 'URGENTE': return 'badge bg-danger';
      case 'ALTA': return 'badge bg-warning';
      case 'NORMAL': return 'badge bg-info';
      case 'BAJA': return 'badge bg-secondary';
      default: return 'badge bg-light text-dark';
    }
  }

  // NUEVO MÉTODO: Obtener clase CSS según porcentaje de avance
  obtenerClaseAvance(porcentaje: number): string {
    if (porcentaje >= 100) return 'bg-success';
    if (porcentaje >= 75) return 'bg-info';
    if (porcentaje >= 50) return 'bg-warning';
    if (porcentaje >= 25) return 'bg-primary';
    return 'bg-secondary';
  }
}