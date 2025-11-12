package com.proyecto.multigiros.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento_inventario")
public class MovimientoInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;
    
    @ManyToOne
    @JoinColumn(name = "id_inventario", nullable = false)
    private Inventario inventario;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false, length = 30)
    private TipoMovimiento tipoMovimiento;
    
    @Column(name = "cantidad", nullable = false, precision = 12, scale = 3)
    private BigDecimal cantidad;
    
    @Column(name = "cantidad_anterior", precision = 12, scale = 3)
    private BigDecimal cantidadAnterior;
    
    @Column(name = "cantidad_nueva", precision = 12, scale = 3)
    private BigDecimal cantidadNueva;
    
    @ManyToOne
    @JoinColumn(name = "id_orden")
    private Orden orden;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @Column(name = "motivo", length = 500)
    private String motivo;
    
    @Column(name = "fecha_movimiento")
    private LocalDateTime fechaMovimiento = LocalDateTime.now();
    
    public enum TipoMovimiento {
        ENTRADA, SALIDA, AJUSTE, DEVOLUCION, PERDIDA
    }
    
    public MovimientoInventario() {}
    
    // Getters y Setters
    public Long getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(Long idMovimiento) { this.idMovimiento = idMovimiento; }
    public Inventario getInventario() { return inventario; }
    public void setInventario(Inventario inventario) { this.inventario = inventario; }
    public TipoMovimiento getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }
    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
    public BigDecimal getCantidadAnterior() { return cantidadAnterior; }
    public void setCantidadAnterior(BigDecimal cantidadAnterior) { this.cantidadAnterior = cantidadAnterior; }
    public BigDecimal getCantidadNueva() { return cantidadNueva; }
    public void setCantidadNueva(BigDecimal cantidadNueva) { this.cantidadNueva = cantidadNueva; }
    public Orden getOrden() { return orden; }
    public void setOrden(Orden orden) { this.orden = orden; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public LocalDateTime getFechaMovimiento() { return fechaMovimiento; }
    public void setFechaMovimiento(LocalDateTime fechaMovimiento) { this.fechaMovimiento = fechaMovimiento; }
}