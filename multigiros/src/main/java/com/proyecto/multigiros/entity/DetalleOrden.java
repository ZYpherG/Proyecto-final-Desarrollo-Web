package com.proyecto.multigiros.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_orden")
public class DetalleOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_orden")
    private Long idDetalleOrden;

    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "id_inventario", nullable = false)
    private Inventario inventario;

    @Column(name = "cantidad_solicitada", nullable = false, precision = 12, scale = 3)
    private BigDecimal cantidadSolicitada;

    @Column(name = "cantidad_despachada", precision = 12, scale = 3)
    private BigDecimal cantidadDespachada = BigDecimal.ZERO;

    @Column(name = "precio_unitario", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", nullable = false, precision = 15, scale = 2)
    private BigDecimal subtotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_detalle", length = 50)
    private EstadoDetalle estadoDetalle = EstadoDetalle.PENDIENTE;

    @Column(name = "observaciones", length = 255)
    private String observaciones;

    public enum EstadoDetalle {
        PENDIENTE, PARCIAL, COMPLETADA, CANCELADA
    }

    public DetalleOrden() {
    }

    // Getters y Setters
    public Long getIdDetalleOrden() {
        return idDetalleOrden;
    }

    public void setIdDetalleOrden(Long idDetalleOrden) {
        this.idDetalleOrden = idDetalleOrden;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public BigDecimal getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(BigDecimal cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public BigDecimal getCantidadDespachada() {
        return cantidadDespachada;
    }

    public void setCantidadDespachada(BigDecimal cantidadDespachada) {
        this.cantidadDespachada = cantidadDespachada;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public EstadoDetalle getEstadoDetalle() {
        return estadoDetalle;
    }

    public void setEstadoDetalle(EstadoDetalle estadoDetalle) {
        this.estadoDetalle = estadoDetalle;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}