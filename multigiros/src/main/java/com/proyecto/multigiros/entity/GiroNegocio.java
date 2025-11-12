package com.proyecto.multigiros.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "giro_negocio")
public class GiroNegocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGiroNegocio;

    @Column(name = "codigo_giro", unique = true, nullable = false, length = 10)
    private String codigoGiro;

    @Column(name = "nombre_giro", nullable = false, length = 100)
    private String nombreGiro;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_giro", nullable = false, length = 50)
    private TipoGiro tipoGiro;

    @Column(name = "presupuesto_anual", precision = 15, scale = 2)
    private BigDecimal presupuestoAnual = BigDecimal.ZERO;

    @Column(name = "responsable", length = 100)
    private String responsable;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public enum TipoGiro {
        EXTRACCION, PROCESO, VENTA_MAQUINARIA, TRANSPORTE, CONSTRUCCION
    }

    public GiroNegocio() {
    }

    public Long getIdGiroNegocio() {
        return idGiroNegocio;
    }

    public void setIdGiroNegocio(Long idGiroNegocio) {
        this.idGiroNegocio = idGiroNegocio;
    }

    public String getCodigoGiro() {
        return codigoGiro;
    }

    public void setCodigoGiro(String codigoGiro) {
        this.codigoGiro = codigoGiro;
    }

    public String getNombreGiro() {
        return nombreGiro;
    }

    public void setNombreGiro(String nombreGiro) {
        this.nombreGiro = nombreGiro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoGiro getTipoGiro() {
        return tipoGiro;
    }

    public void setTipoGiro(TipoGiro tipoGiro) {
        this.tipoGiro = tipoGiro;
    }

    public BigDecimal getPresupuestoAnual() {
        return presupuestoAnual;
    }

    public void setPresupuestoAnual(BigDecimal presupuestoAnual) {
        this.presupuestoAnual = presupuestoAnual;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}