package com.proyecto.multigiros.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "remision")
public class Remision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_remision")
    private Long idRemision;
    
    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private Orden orden;
    
    @Column(name = "numero_remision", unique = true, nullable = false, length = 50)
    private String numeroRemision;
    
    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "id_dispositivo")
    private DispositivoGps dispositivo;
    
    @ManyToOne
    @JoinColumn(name = "id_empleado_responsable")
    private Empleado empleadoResponsable;
    
    @ManyToOne
    @JoinColumn(name = "id_sucursal_origen", nullable = false)
    private Sucursal sucursalOrigen;
    
    @ManyToOne
    @JoinColumn(name = "id_sucursal_destino")
    private Sucursal sucursalDestino;
    
    @Column(name = "descripcion_carga", length = 500)
    private String descripcionCarga;
    
    @Column(name = "peso_total", precision = 12, scale = 2)
    private BigDecimal pesoTotal;
    
    @Column(name = "volumen_total", precision = 12, scale = 3)
    private BigDecimal volumenTotal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 50)
    private EstadoRemision estado = EstadoRemision.PENDIENTE_DESPACHO;
    
    @Column(name = "fecha_entrega_esperada")
    private LocalDate fechaEntregaEsperada;
    
    @Column(name = "fecha_entrega_real")
    private LocalDateTime fechaEntregaReal;
    
    @Column(name = "observaciones", length = 500)
    private String observaciones;
    
    @Column(name = "firma_responsable", length = 100)
    private String firmaResponsable;
    
    public enum EstadoRemision {
        PENDIENTE_DESPACHO, DESPACHADA, EN_TRANSITO, ENTREGADA, PARCIAL, RECHAZADA
    }
    
    public Remision() {}
    
    // Getters y Setters
    public Long getIdRemision() { return idRemision; }
    public void setIdRemision(Long idRemision) { this.idRemision = idRemision; }
    public Orden getOrden() { return orden; }
    public void setOrden(Orden orden) { this.orden = orden; }
    public String getNumeroRemision() { return numeroRemision; }
    public void setNumeroRemision(String numeroRemision) { this.numeroRemision = numeroRemision; }
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
    public DispositivoGps getDispositivo() { return dispositivo; }
    public void setDispositivo(DispositivoGps dispositivo) { this.dispositivo = dispositivo; }
    public Empleado getEmpleadoResponsable() { return empleadoResponsable; }
    public void setEmpleadoResponsable(Empleado empleadoResponsable) { this.empleadoResponsable = empleadoResponsable; }
    public Sucursal getSucursalOrigen() { return sucursalOrigen; }
    public void setSucursalOrigen(Sucursal sucursalOrigen) { this.sucursalOrigen = sucursalOrigen; }
    public Sucursal getSucursalDestino() { return sucursalDestino; }
    public void setSucursalDestino(Sucursal sucursalDestino) { this.sucursalDestino = sucursalDestino; }
    public String getDescripcionCarga() { return descripcionCarga; }
    public void setDescripcionCarga(String descripcionCarga) { this.descripcionCarga = descripcionCarga; }
    public BigDecimal getPesoTotal() { return pesoTotal; }
    public void setPesoTotal(BigDecimal pesoTotal) { this.pesoTotal = pesoTotal; }
    public BigDecimal getVolumenTotal() { return volumenTotal; }
    public void setVolumenTotal(BigDecimal volumenTotal) { this.volumenTotal = volumenTotal; }
    public EstadoRemision getEstado() { return estado; }
    public void setEstado(EstadoRemision estado) { this.estado = estado; }
    public LocalDate getFechaEntregaEsperada() { return fechaEntregaEsperada; }
    public void setFechaEntregaEsperada(LocalDate fechaEntregaEsperada) { this.fechaEntregaEsperada = fechaEntregaEsperada; }
    public LocalDateTime getFechaEntregaReal() { return fechaEntregaReal; }
    public void setFechaEntregaReal(LocalDateTime fechaEntregaReal) { this.fechaEntregaReal = fechaEntregaReal; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getFirmaResponsable() { return firmaResponsable; }
    public void setFirmaResponsable(String firmaResponsable) { this.firmaResponsable = firmaResponsable; }
}