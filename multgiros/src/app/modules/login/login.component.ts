import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  credenciales: any = {
    email: '',
    password: ''
  };
  mensajeError: string = '';

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    // Verificar si ya hay una sesión activa
    const usuarioLogueado = localStorage.getItem('usuarioLogueado');
    if (usuarioLogueado) {
      this.router.navigate(['/ventas']);
    }
  }

  login() {
    let formFormulario: any = document.getElementById("formLogin");
    let formValido: boolean = formFormulario.reportValidity();
    
    if (formValido) {
      this.loginServicio().subscribe(
        (response: any) => {
          if (response.success) {
            // Login exitoso
            localStorage.setItem('usuarioLogueado', JSON.stringify(response.usuario));
            localStorage.setItem('token', 'token_simulado');
            this.router.navigate(['/ventas/dashboard']);
          } else {
            this.mensajeError = response.message;
          }
        },
        (error) => {
          this.mensajeError = 'Error de conexión con el servidor';
        }
      )
    }
  }

  loginServicio(): Observable<any> {
    var httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
    return this.http.post<any>(`${environment.apiUrl}/usuario/login`, this.credenciales, httpOptions)
      .pipe(
        catchError(e => {
          console.error('Error en login:', e);
          return [{ success: false, message: 'Error de conexión' }];
        })
      );
  }

  logout() {
    localStorage.removeItem('usuarioLogueado');
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('usuarioLogueado') !== null;
  }

  getUsuarioLogueado(): any {
    const usuario = localStorage.getItem('usuarioLogueado');
    return usuario ? JSON.parse(usuario) : null;
  }
}