import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-categoria-inventario',
  templateUrl: './categoria-inventario.component.html',
  styleUrls: ['./categoria-inventario.component.css']
})
export class CategoriaInventarioComponent implements OnInit {
  categoria: any = {};
  categorias: any = [];
  girosNegocio: any = []; // Para almacenar los giros de negocio

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarCategorias();
    this.cargarGirosNegocio(); // Cargar giros al inicializar
  }

  cargarGirosNegocio() {
    // Asumiendo que tienes un servicio para cargar giros de negocio
    this.http.get<any>(`${environment.apiUrl}/girosnegocio/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(
        (response: any) => this.girosNegocio = response
      );
  }

  buscarCategorias() {
    this.buscarCategoriasServicio().subscribe(
      (response: any) => this.categorias = response
    )
  }

  buscarCategoriasServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/categoriainventario/listar`)
      .pipe(
        catchError(e => of([])) // Mejor retornar array vacío
      );
  }

  // CORRECIÓN: Cambiar parámetro a string y convertirlo
  buscarPorGiro(idGiroNegocio: string) {
    if (!idGiroNegocio || idGiroNegocio.trim() === '') {
      this.buscarCategorias();
      return;
    }
    
    const id = Number(idGiroNegocio);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de giro válido');
      return;
    }

    this.buscarPorGiroServicio(id).subscribe(
      (response: any) => this.categorias = response
    )
  }

  buscarPorGiroServicio(idGiroNegocio: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/categoriainventario/buscarporgiro/${idGiroNegocio}`)
      .pipe(
        catchError(e => of([]))
      );
  }

  crearCategoria() {
    let formFormulario: any = document.getElementById("formCategoria");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      // Convertir tipos numéricos y booleanos
      if (this.categoria.idGiroNegocio) {
        this.categoria.idGiroNegocio = Number(this.categoria.idGiroNegocio);
      }
      if (typeof this.categoria.activo === 'string') {
        this.categoria.activo = this.categoria.activo === 'true';
      }

      this.crearCategoriaServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  crearCategoriaServicio() {
    var httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
    return this.http.post<any>(`${environment.apiUrl}/categoriainventario/insertar`, this.categoria, httpOptions)
      .pipe(
        catchError(e => of({ error: true }))
      );
  }

  actualizar(categoria: any) {
    if (categoria.error) {
      alert("Error al guardar la categoría");
    } else {
      alert("Categoría guardada exitosamente con el id: " + categoria.idCategoria);
      this.buscarCategorias();
      this.categoria = {};
    }
  }

  Limpiar() {
    this.categoria = {};
  }

  modificar(c: any) {
    this.categoria = { ...c }; // Usar spread operator para evitar mutaciones
  }

  Eliminar(c: any) {
    if (confirm('¿Está seguro de que desea eliminar esta categoría?')) {
      this.eliminarCategoriaServicio(c.idCategoria).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarCategoriaServicio(idCategoria: any) {
    return this.http.delete<any>(`${environment.apiUrl}/categoriainventario/eliminar/${idCategoria}`)
      .pipe(
        catchError(e => of({ error: true }))
      );
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar la categoría");
    } else {
      alert("Categoría eliminada exitosamente");
      this.buscarCategorias();
    }
  }
}