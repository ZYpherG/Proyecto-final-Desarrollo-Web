import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-documento-asociado',
  templateUrl: './documento-asociado.component.html',
  styleUrls: ['./documento-asociado.component.css']
})
export class DocumentoAsociadoComponent implements OnInit {
  documento: any = {};
  documentos: any = [];
  ordenes: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarDocumentos();
    this.cargarListasRelacionadas();
  }

  cargarListasRelacionadas() {
    // Cargar órdenes
    this.http.get<any>(`${environment.apiUrl}/orden/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.ordenes = response);
  }

  buscarDocumentos() {
    this.buscarDocumentosServicio().subscribe(
      (response: any) => this.documentos = response
    )
  }

  buscarDocumentosServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/documentoasociado/listar`)
      .pipe(catchError(e => of([])));
  }

  buscarPorOrden(idOrden: string) {
    if (!idOrden || idOrden.trim() === '') {
      this.buscarDocumentos();
      return;
    }
    
    const id = Number(idOrden);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de orden válido');
      return;
    }

    this.buscarPorOrdenServicio(id).subscribe(
      (response: any) => this.documentos = response
    )
  }

  buscarPorOrdenServicio(idOrden: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/documentoasociado/buscarpororden/${idOrden}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por tipo de documento
  buscarPorTipoDocumento(tipo: string) {
    if (!tipo || tipo.trim() === '') {
      this.buscarDocumentos();
      return;
    }

    this.buscarPorTipoDocumentoServicio(tipo).subscribe(
      (response: any) => this.documentos = response
    )
  }

  buscarPorTipoDocumentoServicio(tipo: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/documentoasociado/buscarportipodocumento/${tipo}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por usuario de carga
  buscarPorUsuarioCarga(usuario: string) {
    if (!usuario || usuario.trim() === '') {
      this.buscarDocumentos();
      return;
    }

    this.buscarPorUsuarioCargaServicio(usuario).subscribe(
      (response: any) => this.documentos = response
    )
  }

  buscarPorUsuarioCargaServicio(usuario: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/documentoasociado/buscarporusuariocarga/${usuario}`)
      .pipe(catchError(e => of([])));
  }

  crearDocumento() {
    let formFormulario: any = document.getElementById("formDocumento");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.convertirTiposDocumento();
      
      this.crearDocumentoServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  convertirTiposDocumento() {
    // Convertir IDs numéricos
    if (this.documento.idOrden) {
      this.documento.idOrden = Number(this.documento.idOrden);
    }

    // Convertir valores enteros
    if (this.documento.tamanoArchivo) {
      this.documento.tamanoArchivo = Number(this.documento.tamanoArchivo);
    }
  }

  crearDocumentoServicio() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(`${environment.apiUrl}/documentoasociado/insertar`, this.documento, httpOptions)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizar(documento: any) {
    if (documento.error) {
      alert("Error al guardar el documento: " + documento.message);
    } else {
      alert("Documento guardado exitosamente con el id: " + documento.idDocumento);
      this.buscarDocumentos();
      this.documento = {};
    }
  }

  Limpiar() {
    this.documento = {};
  }

  modificar(d: any) {
    this.documento = { ...d };
  }

  Eliminar(d: any) {
    if (confirm('¿Está seguro de que desea eliminar este documento?')) {
      this.eliminarDocumentoServicio(d.idDocumento).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarDocumentoServicio(idDocumento: any) {
    return this.http.delete<any>(`${environment.apiUrl}/documentoasociado/eliminar/${idDocumento}`)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar el documento: " + response.message);
    } else {
      alert("Documento eliminado exitosamente");
      this.buscarDocumentos();
    }
  }

  // NUEVO MÉTODO: Formatear tamaño de archivo
  formatearTamanoArchivo(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  }
}