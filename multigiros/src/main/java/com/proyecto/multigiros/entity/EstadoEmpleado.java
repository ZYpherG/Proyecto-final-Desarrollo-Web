package com.proyecto.multigiros.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estado_empleado")
public class EstadoEmpleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_empleado")
    private Long idEstadoEmpleado;
    
    @Column(name = "estado", nullable = false, unique = true, length = 50)
    private String estado;
    
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    
    public EstadoEmpleado() {}
    
    // Getters y Setters
    public Long getIdEstadoEmpleado() { return idEstadoEmpleado; }
    public void setIdEstadoEmpleado(Long idEstadoEmpleado) { this.idEstadoEmpleado = idEstadoEmpleado; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
