import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-bitacora',
  templateUrl: './bitacora.component.html',
  styleUrls: ['./bitacora.component.css']
})
export class BitacoraComponent implements OnInit {
  bitacoras: any = [];
  tablasUnicas: string[] = [];
  usuariosUnicos: string[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarBitacoras();
    this.cargarFiltros();
  }

  cargarFiltros() {
    // Cargar tablas únicas
    this.http.get<any>(`${environment.apiUrl}/bitacora/tablas`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.tablasUnicas = response);

    // Cargar usuarios únicos
    this.http.get<any>(`${environment.apiUrl}/bitacora/usuarios`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.usuariosUnicos = response);
  }

  buscarBitacoras() {
    this.buscarBitacorasServicio().subscribe(
      (response: any) => this.bitacoras = response
    )
  }

  buscarBitacorasServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/bitacora/listar`)
      .pipe(catchError(e => of([])));
  }

  buscarPorTabla(tabla: string) {
    if (!tabla || tabla.trim() === '') {
      this.buscarBitacoras();
      return;
    }
    this.buscarPorTablaServicio(tabla).subscribe(
      (response: any) => this.bitacoras = response
    )
  }

  buscarPorTablaServicio(tabla: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/bitacora/buscarportabla/${tabla}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por tipo de movimiento
  buscarPorTipoMovimiento(tipoMovimiento: string) {
    this.buscarPorTipoMovimientoServicio(tipoMovimiento).subscribe(
      (response: any) => this.bitacoras = response
    )
  }

  buscarPorTipoMovimientoServicio(tipoMovimiento: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/bitacora/buscarportipomovimiento/${tipoMovimiento}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por usuario
  buscarPorUsuario(usuario: string) {
    if (!usuario || usuario.trim() === '') {
      this.buscarBitacoras();
      return;
    }
    this.buscarPorUsuarioServicio(usuario).subscribe(
      (response: any) => this.bitacoras = response
    )
  }

  buscarPorUsuarioServicio(usuario: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/bitacora/buscarporusuario/${usuario}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por rango de fechas
  buscarPorRangoFechas(fechaInicio: string, fechaFin: string) {
    if (!fechaInicio || !fechaFin) {
      alert('Por favor ingrese ambas fechas');
      return;
    }
    this.buscarPorRangoFechasServicio(fechaInicio, fechaFin).subscribe(
      (response: any) => this.bitacoras = response
    )
  }

  buscarPorRangoFechasServicio(fechaInicio: string, fechaFin: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/bitacora/buscarporfecha?inicio=${fechaInicio}&fin=${fechaFin}`)
      .pipe(catchError(e => of([])));
  }
}