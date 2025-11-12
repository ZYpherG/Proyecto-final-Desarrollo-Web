package com.proyecto.multigiros.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;
    
    @ManyToOne
    @JoinColumn(name = "id_orden")
    private Orden orden;
    
    @Column(name = "numero_factura", unique = true, nullable = false, length = 50)
    private String numeroFactura;
    
    @Column(name = "fecha_emision")
    private LocalDate fechaEmision = LocalDate.now();
    
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    @Column(name = "subtotal", precision = 15, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;
    
    @Column(name = "impuestos", precision = 15, scale = 2)
    private BigDecimal impuestos = BigDecimal.ZERO;
    
    @Column(name = "descuentos", precision = 15, scale = 2)
    private BigDecimal descuentos = BigDecimal.ZERO;
    
    @Column(name = "total", precision = 15, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 50)
    private EstadoFactura estado = EstadoFactura.PENDIENTE;
    
    @ManyToOne
    @JoinColumn(name = "id_metodo_pago")
    private MetodoPago metodoPago;
    
    @Column(name = "observaciones", length = 500)
    private String observaciones;
    
    @Column(name = "usuario_creacion", length = 100)
    private String usuarioCreacion;
    
    public enum EstadoFactura {
        PENDIENTE, PAGADA, PARCIAL, CANCELADA, ANULADA
    }
    
    public Factura() {}
    
    // Getters y Setters
    public Long getIdFactura() { return idFactura; }
    public void setIdFactura(Long idFactura) { this.idFactura = idFactura; }
    public Orden getOrden() { return orden; }
    public void setOrden(Orden orden) { this.orden = orden; }
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getImpuestos() { return impuestos; }
    public void setImpuestos(BigDecimal impuestos) { this.impuestos = impuestos; }
    public BigDecimal getDescuentos() { return descuentos; }
    public void setDescuentos(BigDecimal descuentos) { this.descuentos = descuentos; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public EstadoFactura getEstado() { return estado; }
    public void setEstado(EstadoFactura estado) { this.estado = estado; }
    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getUsuarioCreacion() { return usuarioCreacion; }
    public void setUsuarioCreacion(String usuarioCreacion) { this.usuarioCreacion = usuarioCreacion; }
}