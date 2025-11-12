package com.proyecto.multigiros.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "orden")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Long idOrden;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_orden", nullable = false, length = 30)
    private TipoOrden tipoOrden;

    @Column(name = "numero_orden", unique = true, nullable = false, length = 50)
    private String numeroOrden;

    @ManyToOne
    @JoinColumn(name = "id_giro_negocio_origen")
    private GiroNegocio giroNegocioOrigen;

    @ManyToOne
    @JoinColumn(name = "id_giro_negocio_destino")
    private GiroNegocio giroNegocioDestino;

    @ManyToOne
    @JoinColumn(name = "id_giro_negocio_ejecutor")
    private GiroNegocio giroNegocioEjecutor;

    @ManyToOne
    @JoinColumn(name = "id_sucursal_origen")
    private Sucursal sucursalOrigen;

    @ManyToOne
    @JoinColumn(name = "id_sucursal_destino")
    private Sucursal sucursalDestino;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "id_estado_orden", nullable = false)
    private EstadoOrden estadoOrden;

    @ManyToOne
    @JoinColumn(name = "id_empleado_responsable")
    private Empleado empleadoResponsable;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_requerida")
    private LocalDate fechaRequerida;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Column(name = "fecha_inicio_estimada")
    private LocalDate fechaInicioEstimada;

    @Column(name = "fecha_inicio_real")
    private LocalDate fechaInicioReal;

    @Column(name = "duracion_estimada")
    private Integer duracionEstimada;

    @Column(name = "duracion_real")
    private Integer duracionReal;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", length = 20)
    private Prioridad prioridad = Prioridad.NORMAL;

    @Column(name = "porcentaje_avance")
    private Integer porcentajeAvance = 0;

    @Column(name = "subtotal", precision = 15, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(name = "impuestos", precision = 15, scale = 2)
    private BigDecimal impuestos = BigDecimal.ZERO;

    @Column(name = "descuentos", precision = 15, scale = 2)
    private BigDecimal descuentos = BigDecimal.ZERO;

    @Column(name = "total", precision = 15, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "usuario_creacion", length = 100)
    private String usuarioCreacion;

    public enum TipoOrden {
        PEDIDO_CLIENTE, PEDIDO_INTERNO, ORDEN_TRABAJO, ORDEN_COMPRA
    }

    public enum Prioridad {
        BAJA, NORMAL, ALTA, URGENTE
    }

    public Orden() {
    }

    // Getters y Setters
    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }

    public TipoOrden getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(TipoOrden tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public GiroNegocio getGiroNegocioOrigen() {
        return giroNegocioOrigen;
    }

    public void setGiroNegocioOrigen(GiroNegocio giroNegocioOrigen) {
        this.giroNegocioOrigen = giroNegocioOrigen;
    }

    public GiroNegocio getGiroNegocioDestino() {
        return giroNegocioDestino;
    }

    public void setGiroNegocioDestino(GiroNegocio giroNegocioDestino) {
        this.giroNegocioDestino = giroNegocioDestino;
    }

    public GiroNegocio getGiroNegocioEjecutor() {
        return giroNegocioEjecutor;
    }

    public void setGiroNegocioEjecutor(GiroNegocio giroNegocioEjecutor) {
        this.giroNegocioEjecutor = giroNegocioEjecutor;
    }

    public Sucursal getSucursalOrigen() {
        return sucursalOrigen;
    }

    public void setSucursalOrigen(Sucursal sucursalOrigen) {
        this.sucursalOrigen = sucursalOrigen;
    }

    public Sucursal getSucursalDestino() {
        return sucursalDestino;
    }

    public void setSucursalDestino(Sucursal sucursalDestino) {
        this.sucursalDestino = sucursalDestino;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public EstadoOrden getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(EstadoOrden estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public Empleado getEmpleadoResponsable() {
        return empleadoResponsable;
    }

    public void setEmpleadoResponsable(Empleado empleadoResponsable) {
        this.empleadoResponsable = empleadoResponsable;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaRequerida() {
        return fechaRequerida;
    }

    public void setFechaRequerida(LocalDate fechaRequerida) {
        this.fechaRequerida = fechaRequerida;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDate getFechaInicioEstimada() {
        return fechaInicioEstimada;
    }

    public void setFechaInicioEstimada(LocalDate fechaInicioEstimada) {
        this.fechaInicioEstimada = fechaInicioEstimada;
    }

    public LocalDate getFechaInicioReal() {
        return fechaInicioReal;
    }

    public void setFechaInicioReal(LocalDate fechaInicioReal) {
        this.fechaInicioReal = fechaInicioReal;
    }

    public Integer getDuracionEstimada() {
        return duracionEstimada;
    }

    public void setDuracionEstimada(Integer duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public Integer getDuracionReal() {
        return duracionReal;
    }

    public void setDuracionReal(Integer duracionReal) {
        this.duracionReal = duracionReal;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(Integer porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public BigDecimal getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(BigDecimal descuentos) {
        this.descuentos = descuentos;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }
}