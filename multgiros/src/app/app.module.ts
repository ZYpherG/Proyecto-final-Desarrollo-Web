import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClienteComponent } from './modules/cliente/cliente.component';
import { BitacoraComponent } from './modules/bitacora/bitacora.component';
import { CategoriaInventarioComponent } from './modules/categoria-inventario/categoria-inventario.component';
import { ComunicacionClienteComponent } from './modules/comunicacion-cliente/comunicacion-cliente.component';
import { DetalleOrdenComponent } from './modules/detalle-orden/detalle-orden.component';
import { DocumentoAsociadoComponent } from './modules/documento-asociado/documento-asociado.component';
import { EmpleadoComponent } from './modules/empleado/empleado.component';
import { EstadoEmpleadoComponent } from './modules/estado-empleado/estado-empleado.component';
import { EstadoOrdenComponent } from './modules/estado-orden/estado-orden.component';
import { FacturaComponent } from './modules/factura/factura.component';
import { GiroNegocioComponent } from './modules/giro-negocio/giro-negocio.component';
import { InventarioComponent } from './modules/inventario/inventario.component';
import { MetodoPagoComponent } from './modules/metodo-pago/metodo-pago.component';
import { MovimientoInventarioComponent } from './modules/movimiento-inventario/movimiento-inventario.component';
import { OrdenComponent } from './modules/orden/orden.component';
import { PersonaComponent } from './modules/persona/persona.component';
import { ProveedorComponent } from './modules/proveedor/proveedor.component';
import { SucursalComponent } from './modules/sucursal/sucursal.component';
import { UsuarioComponent } from './modules/usuario/usuario.component';
import { LoginComponent } from './modules/login/login.component';
import { MainLayoutComponent } from './modules/main-layout/main-layout.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    ClienteComponent,
    BitacoraComponent,
    CategoriaInventarioComponent,
    ComunicacionClienteComponent,
    DetalleOrdenComponent,
    DocumentoAsociadoComponent,
    EmpleadoComponent,
    EstadoEmpleadoComponent,
    EstadoOrdenComponent,
    FacturaComponent,
    GiroNegocioComponent,
    InventarioComponent,
    MetodoPagoComponent,
    MovimientoInventarioComponent,
    OrdenComponent,
    PersonaComponent,
    ProveedorComponent,
    SucursalComponent,
    UsuarioComponent,
    LoginComponent,
    MainLayoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule, 
    FormsModule,
    CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
