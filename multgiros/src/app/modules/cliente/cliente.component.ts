import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css']
})
export class ClienteComponent implements OnInit {
  cliente: any = {};
  clientes: any = [];
  personas: any = []; // Para almacenar las personas disponibles

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarClientes();
    this.cargarPersonas(); // Cargar personas al inicializar
  }

  cargarPersonas() {
    // Asumiendo que tienes un servicio para cargar personas
    this.http.get<any>(`${environment.apiUrl}/persona/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(
        (response: any) => this.personas = response
      );
  }

  buscarClientes() {
    this.buscarClientesServicio().subscribe(
      (response: any) => this.clientes = response
    )
  }

  buscarClientesServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/cliente/listar`)
      .pipe(
        catchError(e => of([])) // Mejor retornar array vacío
      );
  }

  // CORRECIÓN: Cambiar parámetro a string y convertirlo
  buscarPorPersona(idPersona: string) {
    if (!idPersona || idPersona.trim() === '') {
      this.buscarClientes();
      return;
    }
    
    const id = Number(idPersona);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de persona válido');
      return;
    }

    this.buscarPorPersonaServicio(id).subscribe(
      (response: any) => {
        // Si la respuesta es un objeto individual, convertirlo a array
        this.clientes = Array.isArray(response) ? response : [response];
      }
    )
  }

  buscarPorPersonaServicio(idPersona: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/cliente/buscarporpersona/${idPersona}`)
      .pipe(
        catchError(e => of([]))
      );
  }

  crearCliente() {
    let formFormulario: any = document.getElementById("formCliente");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      // Convertir tipos numéricos y booleanos
      if (this.cliente.idPersona) {
        this.cliente.idPersona = Number(this.cliente.idPersona);
      }
      if (this.cliente.limiteCredito) {
        this.cliente.limiteCredito = Number(this.cliente.limiteCredito);
      }
      if (typeof this.cliente.activo === 'string') {
        this.cliente.activo = this.cliente.activo === 'true';
      }

      this.crearClienteServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  crearClienteServicio() {
    var httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
    return this.http.post<any>(`${environment.apiUrl}/cliente/insertar`, this.cliente, httpOptions)
      .pipe(
        catchError(e => of({ error: true }))
      );
  }

  actualizar(cliente: any) {
    if (cliente.error) {
      alert("Error al guardar el cliente");
    } else {
      alert("Cliente guardado exitosamente con el id: " + cliente.idCliente);
      this.buscarClientes();
      this.cliente = {};
    }
  }

  Limpiar() {
    this.cliente = {};
  }

  modificar(c: any) {
    this.cliente = { ...c }; // Usar spread operator para evitar mutaciones
  }

  Eliminar(c: any) {
    if (confirm('¿Está seguro de que desea eliminar este cliente?')) {
      this.eliminarClienteServicio(c.idCliente).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarClienteServicio(idCliente: any) {
    return this.http.delete<any>(`${environment.apiUrl}/cliente/eliminar/${idCliente}`)
      .pipe(
        catchError(e => of({ error: true }))
      );
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar el cliente");
    } else {
      alert("Cliente eliminado exitosamente");
      this.buscarClientes();
    }
  }
}