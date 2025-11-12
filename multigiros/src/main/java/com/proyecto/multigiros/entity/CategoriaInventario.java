package com.proyecto.multigiros.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria_inventario")
public class CategoriaInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @ManyToOne
    @JoinColumn(name = "id_giro_negocio", nullable = false)
    private GiroNegocio giroNegocio;

    @Column(name = "nombre_categoria", nullable = false, length = 100)
    private String nombreCategoria;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo = true;

    public CategoriaInventario() {
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public GiroNegocio getGiroNegocio() {
        return giroNegocio;
    }

    public void setGiroNegocio(GiroNegocio giroNegocio) {
        this.giroNegocio = giroNegocio;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}