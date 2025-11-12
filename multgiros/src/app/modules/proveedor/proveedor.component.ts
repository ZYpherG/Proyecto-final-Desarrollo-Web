import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-proveedor',
  templateUrl: './proveedor.component.html',
  styleUrls: ['./proveedor.component.css']
})
export class ProveedorComponent implements OnInit {
  proveedor: any = {};
  proveedores: any = [];
  personas: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarProveedores();
    this.cargarListasRelacionadas();
  }

  cargarListasRelacionadas() {
    // Cargar personas
    this.http.get<any>(`${environment.apiUrl}/persona/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.personas = response);
  }

  buscarProveedores() {
    this.buscarProveedoresServicio().subscribe(
      (response: any) => this.proveedores = response
    )
  }

  buscarProveedoresServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/proveedor/listar`)
      .pipe(catchError(e => of([])));
  }

  buscarPorPersona(idPersona: string) {
    if (!idPersona || idPersona.trim() === '') {
      this.buscarProveedores();
      return;
    }
    
    const id = Number(idPersona);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de persona válido');
      return;
    }

    this.buscarPorPersonaServicio(id).subscribe(
      (response: any) => this.proveedores = response ? [response] : []
    )
  }

  buscarPorPersonaServicio(idPersona: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/proveedor/buscarporpersona/${idPersona}`)
      .pipe(catchError(e => of(null)));
  }

  // NUEVO MÉTODO: Buscar por nombre comercial
  buscarPorNombreComercial(nombre: string) {
    if (!nombre || nombre.trim() === '') {
      this.buscarProveedores();
      return;
    }

    this.buscarPorNombreComercialServicio(nombre).subscribe(
      (response: any) => this.proveedores = response ? [response] : []
    )
  }

  buscarPorNombreComercialServicio(nombre: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/proveedor/buscarpornombrecomercial/${nombre}`)
      .pipe(catchError(e => of(null)));
  }

  // NUEVO MÉTODO: Buscar por categoría de proveedor
  buscarPorCategoria(categoria: string) {
    if (!categoria || categoria.trim() === '') {
      this.buscarProveedores();
      return;
    }

    this.buscarPorCategoriaServicio(categoria).subscribe(
      (response: any) => this.proveedores = response
    )
  }

  buscarPorCategoriaServicio(categoria: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/proveedor/buscarporcategoria/${categoria}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar proveedores activos
  buscarActivos() {
    this.buscarPorActivoServicio(true).subscribe(
      (response: any) => this.proveedores = response
    )
  }

  buscarPorActivoServicio(activo: boolean): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/proveedor/buscarporactivo/${activo}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por calificación mínima
  buscarPorCalificacionMinima(calificacion: string) {
    if (!calificacion || calificacion.trim() === '') {
      this.buscarProveedores();
      return;
    }
    
    const calif = Number(calificacion);
    if (isNaN(calif) || calif < 0 || calif > 5) {
      alert('Por favor ingrese una calificación válida (0-5)');
      return;
    }

    this.buscarPorCalificacionMinimaServicio(calif).subscribe(
      (response: any) => this.proveedores = response
    )
  }

  buscarPorCalificacionMinimaServicio(calificacion: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/proveedor/buscarporcalificacion/${calificacion}`)
      .pipe(catchError(e => of([])));
  }

  crearProveedor() {
    let formFormulario: any = document.getElementById("formProveedor");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.convertirTiposProveedor();
      
      this.crearProveedorServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  convertirTiposProveedor() {
    // Convertir IDs numéricos
    if (this.proveedor.idPersona) {
      this.proveedor.idPersona = Number(this.proveedor.idPersona);
    }

    // Convertir valores decimales
    const camposDecimales = ['calificacion', 'limiteCredito'];
    camposDecimales.forEach(campo => {
      if (this.proveedor[campo]) {
        this.proveedor[campo] = Number(this.proveedor[campo]);
      }
    });

    // Convertir valores enteros
    if (this.proveedor.diasPlazo) {
      this.proveedor.diasPlazo = Number(this.proveedor.diasPlazo);
    }

    // Convertir booleanos
    if (typeof this.proveedor.activo === 'string') {
      this.proveedor.activo = this.proveedor.activo === 'true';
    }
  }

  crearProveedorServicio() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(`${environment.apiUrl}/proveedor/insertar`, this.proveedor, httpOptions)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizar(proveedor: any) {
    if (proveedor.error) {
      alert("Error al guardar el proveedor: " + proveedor.message);
    } else {
      alert("Proveedor guardado exitosamente con el id: " + proveedor.idProveedor);
      this.buscarProveedores();
      this.proveedor = {};
    }
  }

  Limpiar() {
    this.proveedor = {};
  }

  modificar(p: any) {
    this.proveedor = { ...p };
  }

  Eliminar(p: any) {
    if (confirm('¿Está seguro de que desea eliminar este proveedor?')) {
      this.eliminarProveedorServicio(p.idProveedor).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarProveedorServicio(idProveedor: any) {
    return this.http.delete<any>(`${environment.apiUrl}/proveedor/eliminar/${idProveedor}`)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar el proveedor: " + response.message);
    } else {
      alert("Proveedor eliminado exitosamente");
      this.buscarProveedores();
    }
  }

  // NUEVO MÉTODO: Calcular estrellas para display
  obtenerEstrellas(calificacion: number): number[] {
    const estrellasLlenas = Math.floor(calificacion);
    const estrellas = Array(5).fill(0);
    
    for (let i = 0; i < estrellasLlenas; i++) {
      estrellas[i] = 1;
    }
    
    if (calificacion - estrellasLlenas >= 0.5) {
      estrellas[estrellasLlenas] = 0.5;
    }
    
    return estrellas;
  }

  // NUEVO MÉTODO: Actualizar calificación
  actualizarCalificacion(proveedor: any, nuevaCalificacion: number) {
    proveedor.calificacion = nuevaCalificacion;
    this.crearProveedorServicio().subscribe(
      (response: any) => {
        if (!response.error) {
          alert("Calificación actualizada exitosamente");
          this.buscarProveedores();
        }
      }
    );
  }
}