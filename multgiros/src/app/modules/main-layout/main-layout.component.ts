import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-layout',
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.css']
})
export class MainLayoutComponent implements OnInit {
  selectedTab: string = 'alerta';
  selectedCategory: string = 'operaciones';

  // Categorías organizadas
  categories = [
    { 
      id: 'operaciones', 
      name: 'Operaciones', 
      icon: 'bi bi-gear-wide-connected',
      tabs: ['orden', 'detalle', 'remision', 'estado-orden']
    },
    { 
      id: 'clientes', 
      name: 'Clientes', 
      icon: 'bi bi-people',
      tabs: ['cliente', 'comunicacion', 'documento']
    },
    { 
      id: 'inventario', 
      name: 'Inventario', 
      icon: 'bi bi-boxes',
      tabs: ['inventario', 'categoria', 'movimiento', 'proveedor']
    },
    { 
      id: 'finanzas', 
      name: 'Finanzas', 
      icon: 'bi bi-credit-card',
      tabs: ['factura', 'metodo-pago', 'giro']
    },
    { 
      id: 'personas', 
      name: 'Personas', 
      icon: 'bi bi-person-badge',
      tabs: ['persona', 'empleado', 'estado-empleado']
    },
    { 
      id: 'seguridad', 
      name: 'Seguridad', 
      icon: 'bi bi-shield-lock',
      tabs: ['usuario', 'bitacora']
    },
    { 
      id: 'configuracion', 
      name: 'Configuración', 
      icon: 'bi bi-sliders',
      tabs: ['sucursal']
    }
  ];

  // Todos los tabs disponibles
  allTabs = [
    { id: 'bitacora', name: 'Bitácora' },
    { id: 'categoria', name: 'Categorías' },
    { id: 'cliente', name: 'Clientes' },
    { id: 'comunicacion', name: 'Comunicaciones' },
    { id: 'detalle', name: 'Detalles Orden' },
    { id: 'documento', name: 'Documentos' },
    { id: 'empleado', name: 'Empleados' },
    { id: 'estado-empleado', name: 'Estados Empleado' },
    { id: 'estado-orden', name: 'Estados Orden' },
    { id: 'factura', name: 'Facturas' },
    { id: 'giro', name: 'Giros Negocio' },
    { id: 'inventario', name: 'Inventario' },
    { id: 'metodo-pago', name: 'Métodos Pago' },
    { id: 'movimiento', name: 'Movimientos' },
    { id: 'orden', name: 'Órdenes' },
    { id: 'persona', name: 'Personas' },
    { id: 'proveedor', name: 'Proveedores' },
    { id: 'remision', name: 'Remisiones' },
    { id: 'sucursal', name: 'Sucursales' },
    { id: 'usuario', name: 'Usuarios' }
  ];

  constructor(private router: Router) { }

  ngOnInit(): void {
    if (!this.isLoggedIn()) {
      this.router.navigate(['/login']);
    }
  }

  selectCategory(categoryId: string): void {
    this.selectedCategory = categoryId;
    // Seleccionar el primer tab de la categoría
    const category = this.categories.find(c => c.id === categoryId);
    if (category && category.tabs.length > 0) {
      this.selectedTab = category.tabs[0];
    }
  }

  selectTab(tabId: string): void {
    this.selectedTab = tabId;
  }

  getCategoryTabs() {
    const category = this.categories.find(c => c.id === this.selectedCategory);
    if (category) {
      return this.allTabs.filter(tab => category.tabs.includes(tab.id));
    }
    return [];
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('usuarioLogueado') !== null;
  }

  logout(): void {
    localStorage.removeItem('usuarioLogueado');
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  getUsuarioLogueado(): any {
    const usuario = localStorage.getItem('usuarioLogueado');
    return usuario ? JSON.parse(usuario) : null;
  }
}