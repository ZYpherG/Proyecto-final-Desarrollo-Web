import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-inventario',
  templateUrl: './inventario.component.html',
  styleUrls: ['./inventario.component.css']
})
export class InventarioComponent implements OnInit {
  inventario: any = {};
  inventarios: any = [];
  categorias: any = [];
  sucursales: any = [];
  proveedores: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarInventarios();
    this.cargarListasRelacionadas();
  }

  cargarListasRelacionadas() {
    // Cargar categorías
    this.http.get<any>(`${environment.apiUrl}/categoriainventario/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.categorias = response);

    // Cargar sucursales
    this.http.get<any>(`${environment.apiUrl}/sucursal/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.sucursales = response);

    // Cargar proveedores
    this.http.get<any>(`${environment.apiUrl}/proveedor/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.proveedores = response);
  }

  buscarInventarios() {
    this.buscarInventariosServicio().subscribe(
      (response: any) => this.inventarios = response
    )
  }

  buscarInventariosServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/inventario/listar`)
      .pipe(catchError(e => of([])));
  }

  buscarPorCodigo(codigo: string) {
    if (!codigo || codigo.trim() === '') {
      this.buscarInventarios();
      return;
    }

    this.buscarPorCodigoServicio(codigo).subscribe(
      (response: any) => this.inventarios = response ? [response] : []
    )
  }

  buscarPorCodigoServicio(codigo: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/inventario/buscarporcodigo/${codigo}`)
      .pipe(catchError(e => of(null)));
  }

  // NUEVO MÉTODO: Buscar por código de barras
  buscarPorCodigoBarras(codigoBarras: string) {
    if (!codigoBarras || codigoBarras.trim() === '') {
      this.buscarInventarios();
      return;
    }

    this.buscarPorCodigoBarrasServicio(codigoBarras).subscribe(
      (response: any) => this.inventarios = response ? [response] : []
    )
  }

  buscarPorCodigoBarrasServicio(codigoBarras: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/inventario/buscarporcodigobarras/${codigoBarras}`)
      .pipe(catchError(e => of(null)));
  }

  // NUEVO MÉTODO: Buscar por categoría
  buscarPorCategoria(idCategoria: string) {
    if (!idCategoria || idCategoria.trim() === '') {
      this.buscarInventarios();
      return;
    }
    
    const id = Number(idCategoria);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de categoría válido');
      return;
    }

    this.buscarPorCategoriaServicio(id).subscribe(
      (response: any) => this.inventarios = response
    )
  }

  buscarPorCategoriaServicio(idCategoria: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/inventario/buscarporCategoria/${idCategoria}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por sucursal
  buscarPorSucursal(idSucursal: string) {
    if (!idSucursal || idSucursal.trim() === '') {
      this.buscarInventarios();
      return;
    }
    
    const id = Number(idSucursal);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de sucursal válido');
      return;
    }

    this.buscarPorSucursalServicio(id).subscribe(
      (response: any) => this.inventarios = response
    )
  }

  buscarPorSucursalServicio(idSucursal: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/inventario/buscarporSucursal/${idSucursal}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por tipo de producto
  buscarPorTipoProducto(tipo: string) {
    if (!tipo || tipo.trim() === '') {
      this.buscarInventarios();
      return;
    }

    this.buscarPorTipoProductoServicio(tipo).subscribe(
      (response: any) => this.inventarios = response
    )
  }

  buscarPorTipoProductoServicio(tipo: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/inventario/buscarportipoproducto/${tipo}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar productos con stock bajo
  buscarStockBajo() {
    this.buscarPorStockBajoServicio().subscribe(
      (response: any) => this.inventarios = response
    )
  }

  buscarPorStockBajoServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/inventario/buscarstockbajo`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar productos activos
  buscarActivos() {
    this.buscarPorActivoServicio(true).subscribe(
      (response: any) => this.inventarios = response
    )
  }

  buscarPorActivoServicio(activo: boolean): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/inventario/buscarporactivo/${activo}`)
      .pipe(catchError(e => of([])));
  }

  crearInventario() {
    let formFormulario: any = document.getElementById("formInventario");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.convertirTiposInventario();
      
      this.crearInventarioServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  convertirTiposInventario() {
    // Convertir IDs numéricos
    const camposNumericos = ['idCategoria', 'idSucursal', 'idProveedor'];
    camposNumericos.forEach(campo => {
      if (this.inventario[campo]) {
        this.inventario[campo] = Number(this.inventario[campo]);
      }
    });

    // Convertir valores decimales
    const camposDecimales = [
      'existenciaActual', 'existenciaMinima', 'existenciaMaxima',
      'costoPromedio', 'precioVenta'
    ];
    camposDecimales.forEach(campo => {
      if (this.inventario[campo]) {
        this.inventario[campo] = Number(this.inventario[campo]);
      }
    });

    // Convertir valores enteros
    if (this.inventario.diasSinMovimiento) {
      this.inventario.diasSinMovimiento = Number(this.inventario.diasSinMovimiento);
    }

    // Convertir booleanos
    if (typeof this.inventario.activo === 'string') {
      this.inventario.activo = this.inventario.activo === 'true';
    }
  }

  crearInventarioServicio() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(`${environment.apiUrl}/inventario/insertar`, this.inventario, httpOptions)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizar(inventario: any) {
    if (inventario.error) {
      alert("Error al guardar el inventario: " + inventario.message);
    } else {
      alert("Inventario guardado exitosamente con el id: " + inventario.idInventario);
      this.buscarInventarios();
      this.inventario = {};
    }
  }

  Limpiar() {
    this.inventario = {};
  }

  modificar(i: any) {
    this.inventario = { ...i };
  }

  Eliminar(i: any) {
    if (confirm('¿Está seguro de que desea eliminar este inventario?')) {
      this.eliminarInventarioServicio(i.idInventario).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarInventarioServicio(idInventario: any) {
    return this.http.delete<any>(`${environment.apiUrl}/inventario/eliminar/${idInventario}`)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar el inventario: " + response.message);
    } else {
      alert("Inventario eliminado exitosamente");
      this.buscarInventarios();
    }
  }

  // NUEVO MÉTODO: Verificar stock bajo
  tieneStockBajo(inventario: any): boolean {
    return inventario.existenciaActual <= inventario.existenciaMinima;
  }

  // NUEVO MÉTODO: Calcular margen de ganancia
  calcularMargenGanancia(inventario: any): number {
    if (!inventario.costoPromedio || !inventario.precioVenta) return 0;
    return ((inventario.precioVenta - inventario.costoPromedio) / inventario.costoPromedio) * 100;
  }
}