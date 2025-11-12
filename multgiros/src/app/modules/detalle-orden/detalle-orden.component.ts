import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-detalle-orden',
  templateUrl: './detalle-orden.component.html',
  styleUrls: ['./detalle-orden.component.css']
})
export class DetalleOrdenComponent implements OnInit {
  detalle: any = {};
  detalles: any = [];
  ordenes: any = [];
  inventarios: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarDetalles();
    this.cargarListasRelacionadas();
  }

  cargarListasRelacionadas() {
    // Cargar órdenes
    this.http.get<any>(`${environment.apiUrl}/orden/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.ordenes = response);

    // Cargar inventarios
    this.http.get<any>(`${environment.apiUrl}/inventario/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.inventarios = response);
  }

  buscarDetalles() {
    this.buscarDetallesServicio().subscribe(
      (response: any) => this.detalles = response
    )
  }

  buscarDetallesServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/detalleorden/listar`)
      .pipe(catchError(e => of([])));
  }

  buscarPorOrden(idOrden: string) {
    if (!idOrden || idOrden.trim() === '') {
      this.buscarDetalles();
      return;
    }
    
    const id = Number(idOrden);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de orden válido');
      return;
    }

    this.buscarPorOrdenServicio(id).subscribe(
      (response: any) => this.detalles = response
    )
  }

  buscarPorOrdenServicio(idOrden: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/detalleorden/buscarpororden/${idOrden}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por inventario
  buscarPorInventario(idInventario: string) {
    if (!idInventario || idInventario.trim() === '') {
      this.buscarDetalles();
      return;
    }
    
    const id = Number(idInventario);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de inventario válido');
      return;
    }

    this.buscarPorInventarioServicio(id).subscribe(
      (response: any) => this.detalles = response
    )
  }

  buscarPorInventarioServicio(idInventario: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/detalleorden/buscarporinventario/${idInventario}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por estado del detalle
  buscarPorEstado(estado: string) {
    if (!estado || estado.trim() === '') {
      this.buscarDetalles();
      return;
    }

    this.buscarPorEstadoServicio(estado).subscribe(
      (response: any) => this.detalles = response
    )
  }

  buscarPorEstadoServicio(estado: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/detalleorden/buscarestado/${estado}`)
      .pipe(catchError(e => of([])));
  }

  crearDetalle() {
    let formFormulario: any = document.getElementById("formDetalle");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.convertirTiposDetalle();
      
      this.crearDetalleServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  convertirTiposDetalle() {
  // Extraer IDs de los objetos anidados
  if (this.detalle.orden && this.detalle.orden.idOrden) {
    this.detalle.idOrden = Number(this.detalle.orden.idOrden);
  }

  if (this.detalle.inventario && this.detalle.inventario.idInventario) {
    this.detalle.idInventario = Number(this.detalle.inventario.idInventario);
  }

  // Convertir valores numéricos
  const camposDecimales = ['cantidadSolicitada', 'cantidadDespachada', 'precioUnitario', 'subtotal'];
  camposDecimales.forEach(campo => {
    if (this.detalle[campo]) {
      this.detalle[campo] = Number(this.detalle[campo]);
    }
  });
}

  crearDetalleServicio() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(`${environment.apiUrl}/detalleorden/insertar`, this.detalle, httpOptions)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizar(detalle: any) {
    if (detalle.error) {
      alert("Error al guardar el detalle: " + detalle.message);
    } else {
      alert("Detalle guardado exitosamente con el id: " + detalle.idDetalleOrden);
      this.buscarDetalles();
      this.detalle = {};
    }
  }

  Limpiar() {
    this.detalle = {};
  }

  modificar(d: any) {
    this.detalle = { ...d };
  }

  Eliminar(d: any) {
    if (confirm('¿Está seguro de que desea eliminar este detalle?')) {
      this.eliminarDetalleServicio(d.idDetalleOrden).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarDetalleServicio(idDetalle: any) {
    return this.http.delete<any>(`${environment.apiUrl}/detalleorden/eliminar/${idDetalle}`)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar el detalle: " + response.message);
    } else {
      alert("Detalle eliminado exitosamente");
      this.buscarDetalles();
    }
  }

  // NUEVO MÉTODO: Calcular subtotal automáticamente
  calcularSubtotal() {
    if (this.detalle.cantidadSolicitada && this.detalle.precioUnitario) {
      this.detalle.subtotal = Number(this.detalle.cantidadSolicitada) * Number(this.detalle.precioUnitario);
    }
  }
}