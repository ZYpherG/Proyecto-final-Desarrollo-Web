import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {
  usuario: any = {};
  usuarios: any = [];
  empleados: any = [];
  girosNegocio: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.buscarUsuarios();
    this.cargarListasRelacionadas();
  }

  cargarListasRelacionadas() {
    // Cargar empleados
    this.http.get<any>(`${environment.apiUrl}/empleado/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.empleados = response);

    // Cargar giros de negocio
    this.http.get<any>(`${environment.apiUrl}/gironegocio/listar`)
      .pipe(catchError(e => of([])))
      .subscribe(response => this.girosNegocio = response);
  }

  buscarUsuarios() {
    this.buscarUsuariosServicio().subscribe(
      (response: any) => this.usuarios = response
    )
  }

  buscarUsuariosServicio(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/usuario/listar`)
      .pipe(catchError(e => of([])));
  }

  buscarPorNombreUsuario(nombreUsuario: string) {
    if (!nombreUsuario || nombreUsuario.trim() === '') {
      this.buscarUsuarios();
      return;
    }

    this.buscarPorNombreUsuarioServicio(nombreUsuario).subscribe(
      (response: any) => this.usuarios = response ? [response] : []
    )
  }

  buscarPorNombreUsuarioServicio(nombreUsuario: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/usuario/buscarporusuario/${nombreUsuario}`)
      .pipe(catchError(e => of(null)));
  }

  // NUEVO MÉTODO: Buscar por email
  buscarPorEmail(email: string) {
    if (!email || email.trim() === '') {
      this.buscarUsuarios();
      return;
    }

    this.buscarPorEmailServicio(email).subscribe(
      (response: any) => this.usuarios = response ? [response] : []
    )
  }

  buscarPorEmailServicio(email: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/usuario/buscarportemail/${email}`)
      .pipe(catchError(e => of(null)));
  }

  // NUEVO MÉTODO: Buscar por rol
  buscarPorRol(rol: string) {
    if (!rol || rol.trim() === '') {
      this.buscarUsuarios();
      return;
    }

    this.buscarPorRolServicio(rol).subscribe(
      (response: any) => this.usuarios = response
    )
  }

  buscarPorRolServicio(rol: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/usuario/buscarporRol/${rol}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar por giro de negocio
  buscarPorGiroNegocio(idGiro: string) {
    if (!idGiro || idGiro.trim() === '') {
      this.buscarUsuarios();
      return;
    }
    
    const id = Number(idGiro);
    if (isNaN(id)) {
      alert('Por favor ingrese un ID de giro válido');
      return;
    }

    this.buscarPorGiroNegocioServicio(id).subscribe(
      (response: any) => this.usuarios = response
    )
  }

  buscarPorGiroNegocioServicio(idGiro: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/usuario/buscarporGiroNegocio/${idGiro}`)
      .pipe(catchError(e => of([])));
  }

  // NUEVO MÉTODO: Buscar usuarios activos
  buscarActivos() {
    this.buscarPorActivoServicio(true).subscribe(
      (response: any) => this.usuarios = response
    )
  }

  buscarPorActivoServicio(activo: boolean): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/usuario/buscarporactivo/${activo}`)
      .pipe(catchError(e => of([])));
  }

  crearUsuario() {
    let formFormulario: any = document.getElementById("formUsuario");
    let formValido: boolean = formFormulario.reportValidity();
    if (formValido) {
      this.convertirTiposUsuario();
      
      this.crearUsuarioServicio().subscribe(
        (response: any) => this.actualizar(response)
      )
    }
  }

  convertirTiposUsuario() {
    // Convertir IDs numéricos
    const camposNumericos = ['idEmpleado', 'idGiroNegocio'];
    camposNumericos.forEach(campo => {
      if (this.usuario[campo]) {
        this.usuario[campo] = Number(this.usuario[campo]);
      }
    });

    // Convertir valores enteros
    if (this.usuario.intentosFallidos) {
      this.usuario.intentosFallidos = Number(this.usuario.intentosFallidos);
    }

    // Convertir booleanos
    const camposBooleanos = ['activo', 'bloqueado'];
    camposBooleanos.forEach(campo => {
      if (typeof this.usuario[campo] === 'string') {
        this.usuario[campo] = this.usuario[campo] === 'true';
      }
    });
  }

  crearUsuarioServicio() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(`${environment.apiUrl}/usuario/insertar`, this.usuario, httpOptions)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizar(usuario: any) {
    if (usuario.error) {
      alert("Error al guardar el usuario: " + usuario.message);
    } else {
      alert("Usuario guardado exitosamente con el id: " + usuario.idUsuario);
      this.buscarUsuarios();
      this.usuario = {};
    }
  }

  Limpiar() {
    this.usuario = {};
  }

  modificar(u: any) {
    this.usuario = { ...u };
  }

  Eliminar(u: any) {
    if (confirm('¿Está seguro de que desea eliminar este usuario?')) {
      this.eliminarUsuarioServicio(u.idUsuario).subscribe(
        (response: any) => this.actualizarEliminar(response)
      )
    }
  }

  eliminarUsuarioServicio(idUsuario: any) {
    return this.http.delete<any>(`${environment.apiUrl}/usuario/eliminar/` + idUsuario)
      .pipe(catchError(e => of({ error: true, message: e.message })));
  }

  actualizarEliminar(response: any) {
    if (response.error) {
      alert("Error al eliminar el usuario: " + response.message);
    } else {
      alert("Usuario eliminado exitosamente");
      this.buscarUsuarios();
    }
  }

  // NUEVO MÉTODO: Bloquear usuario
  bloquearUsuario(usuario: any) {
    usuario.bloqueado = true;
    this.crearUsuarioServicio().subscribe(
      (response: any) => {
        if (!response.error) {
          alert("Usuario bloqueado exitosamente");
          this.buscarUsuarios();
        }
      }
    );
  }

  // NUEVO MÉTODO: Desbloquear usuario
  desbloquearUsuario(usuario: any) {
    usuario.bloqueado = false;
    usuario.intentosFallidos = 0;
    this.crearUsuarioServicio().subscribe(
      (response: any) => {
        if (!response.error) {
          alert("Usuario desbloqueado exitosamente");
          this.buscarUsuarios();
        }
      }
    );
  }

  // NUEVO MÉTODO: Activar/desactivar usuario
  toggleActivo(usuario: any) {
    usuario.activo = !usuario.activo;
    this.crearUsuarioServicio().subscribe(
      (response: any) => {
        if (!response.error) {
          const estado = usuario.activo ? 'activado' : 'desactivado';
          alert(`Usuario ${estado} exitosamente`);
          this.buscarUsuarios();
        }
      }
    );
  }

  // NUEVO MÉTODO: Obtener clase CSS según estado
  obtenerClaseEstado(activo: boolean, bloqueado: boolean): string {
    if (bloqueado) return 'badge bg-danger';
    return activo ? 'badge bg-success' : 'badge bg-warning';
  }

  // NUEVO MÉTODO: Obtener texto según estado
  obtenerTextoEstado(activo: boolean, bloqueado: boolean): string {
    if (bloqueado) return 'Bloqueado';
    return activo ? 'Activo' : 'Inactivo';
  }

  // NUEVO MÉTODO: Obtener clase CSS según rol
  obtenerClaseRol(rol: string): string {
    switch (rol) {
      case 'ADMIN': return 'badge bg-danger';
      case 'GERENTE': return 'badge bg-warning';
      case 'SUPERVISOR': return 'badge bg-info';
      case 'CONTADOR': return 'badge bg-primary';
      case 'VENDEDOR': return 'badge bg-success';
      case 'OPERADOR': return 'badge bg-secondary';
      default: return 'badge bg-light text-dark';
    }
  }
}