package com.proyecto.multigiros.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "metodo_pago")
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo_pago")
    private Long idMetodoPago;
    
    @Column(name = "nombre_metodo", nullable = false, unique = true, length = 50)
    private String nombreMetodo;
    
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    public MetodoPago() {}
    
    // Getters y Setters
    public Long getIdMetodoPago() { return idMetodoPago; }
    public void setIdMetodoPago(Long idMetodoPago) { this.idMetodoPago = idMetodoPago; }
    public String getNombreMetodo() { return nombreMetodo; }
    public void setNombreMetodo(String nombreMetodo) { this.nombreMetodo = nombreMetodo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}