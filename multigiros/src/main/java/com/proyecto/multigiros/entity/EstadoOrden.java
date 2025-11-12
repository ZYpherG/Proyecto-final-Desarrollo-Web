package com.proyecto.multigiros.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_orden")
public class EstadoOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_orden")
    private Long idEstadoOrden;
    
    @Column(name = "estado", nullable = false, unique = true, length = 50)
    private String estado;
    
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    
    @Column(name = "es_final")
    private Boolean esFinal = false;
    
    public EstadoOrden() {}
    
    // Getters y Setters
    public Long getIdEstadoOrden() { return idEstadoOrden; }
    public void setIdEstadoOrden(Long idEstadoOrden) { this.idEstadoOrden = idEstadoOrden; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Boolean getEsFinal() { return esFinal; }
    public void setEsFinal(Boolean esFinal) { this.esFinal = esFinal; }
}