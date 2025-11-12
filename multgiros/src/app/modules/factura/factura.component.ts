import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-factura',
  templateUrl: './factura.component.html',
  styleUrls: ['./factura.component.css']
})
export class FacturaComponent implements OnInit {
  factura: any = {};
  facturas: any = [];
  ordenes: any = [];
  metodosPago: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarFacturas();
    this.cargarListasRelacionadas();
  }

  cargarListasRelacionadas() {
    // Cargar órdenes
    this.http.get<any>(`${environment.apiUrl}/orden/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.ordenes = response);

    // Cargar métodos de pago
    this.http.get<any>(`${environment.apiUrl}/metodopago/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.metodosPago = response);
  }

  buscarFacturas() {
    this.buscarFacturasServicio().subscribe(
      (response: any) => this.facturas = response
    )
  }

  buscarFacturasServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/factura/listar`)
      .pipe(catchError(e => of([])));
  }

  buscarPorNumero(numero: string) {
    if (!numero || numero.trim() === '') {
      this.buscarFacturas();
      return;
    }

    this.buscarPorNumeroServicio(numero).subscribe(
      (response: any) => this.facturas = response ? [response] : []
    )
  }

  buscarPorNumeroServicio(numero: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/factura/buscarpornumero/${numero}`)
      .pipe(catchError(e => of(null)));
  }

  buscarPorOrden(idOrden: string) {
    if (!idOrden || idOrden.trim() === '') {
      this.buscarFacturas();
      return;
    }

    const id = Number(idOrden);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de orden válido');
      return;
    }

    this.buscarPorOrdenServicio(id).subscribe(
      (response: any) => this.facturas = response
    )
  }

  buscarPorOrdenServicio(idOrden: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/factura/buscarpororden/${idOrden}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por estado de factura
  buscarPorEstado(estado: string) {
    if (!estado || estado.trim() === '') {
      this.buscarFacturas();
      return;
    }

    this.buscarPorEstadoServicio(estado).subscribe(
      (response: any) => this.facturas = response
    )
  }

  buscarPorEstadoServicio(estado: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/factura/buscarporEstado/${estado}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar facturas vencidas
  buscarVencidas() {
    const hoy = new Date().toISOString().split('T')[0];
    this.buscarPorVencimientoServicio(hoy).subscribe(
      (response: any) => this.facturas = response
    )
  }

  buscarPorVencimientoServicio(fecha: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/factura/buscarporvencimiento/${fecha}`)
      .pipe(catchError(e => of([])));
  }

  crearFactura() {
    let formFormulario: any = document.getElementById("formFactura");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.convertirTiposFactura();

      this.crearFacturaServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  convertirTiposFactura() {
    // Convertir IDs numéricos
    const camposNumericos = ['idOrden', 'idMetodoPago'];
    camposNumericos.forEach(campo => {
      if (this.factura[campo]) {
        this.factura[campo] = Number(this.factura[campo]);
      }
    });

    // Convertir valores decimales
    const camposDecimales = ['subtotal', 'impuestos', 'descuentos', 'total'];
    camposDecimales.forEach(campo => {
      if (this.factura[campo]) {
        this.factura[campo] = Number(this.factura[campo]);
      }
    });
  }

  crearFacturaServicio() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(`${environment.apiUrl}/factura/insertar`, this.factura, httpOptions)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizar(factura: any) {
    if (factura.error) {
      alert("Error al guardar la factura: " + factura.message);
    } else {
      alert("Factura guardada exitosamente con el id: " + factura.idFactura);
      this.buscarFacturas();
      this.factura = {};
    }
  }

  Limpiar() {
    this.factura = {};
  }

  modificar(f: any) {
    this.factura = { ...f };
  }

  Eliminar(f: any) {
    if (confirm('¿Está seguro de que desea eliminar esta factura?')) {
      this.eliminarFacturaServicio(f.idFactura).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarFacturaServicio(idFactura: any) {
    return this.http.delete<any>(`${environment.apiUrl}/factura/eliminar/${idFactura}`)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar la factura: " + response.message);
    } else {
      alert("Factura eliminada exitosamente");
      this.buscarFacturas();
    }
  }

  // NUEVO MÉTODO: Calcular total automáticamente
  calcularTotal() {
    const subtotal = Number(this.factura.subtotal) || 0;
    const impuestos = Number(this.factura.impuestos) || 0;
    const descuentos = Number(this.factura.descuentos) || 0;

    this.factura.total = subtotal + impuestos - descuentos;
  }

  // NUEVO MÉTODO: Marcar como pagada
  marcarComoPagada(factura: any) {
    factura.estado = 'PAGADA';
    this.crearFacturaServicio().subscribe(
      (response: any) => {
        if (!response.error) {
          alert("Factura marcada como pagada");
          this.buscarFacturas();
        }
      }
    );
  }

  // Método para determinar si una factura está vencida
  estaVencida(factura: any): boolean {
    if (!factura.fechaVencimiento) return false; // sin fecha, no se marca como vencida
    const hoy = new Date();
    const fechaVenc = new Date(factura.fechaVencimiento);
    return fechaVenc < hoy && factura.estado !== 'PAGADA';
  }
}