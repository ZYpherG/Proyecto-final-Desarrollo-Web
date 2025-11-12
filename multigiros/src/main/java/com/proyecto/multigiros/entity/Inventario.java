package com.proyecto.multigiros.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaInventario categoria;

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_producto", nullable = false, length = 30)
    private TipoProducto tipoProducto;

    @Column(name = "codigo_producto", unique = true, nullable = false, length = 50)
    private String codigoProducto;

    @Column(name = "nombre_producto", nullable = false, length = 200)
    private String nombreProducto;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "codigo_barras", unique = true, length = 50)
    private String codigoBarras;

    @Column(name = "numero_parte", length = 50)
    private String numeroParte;

    @Column(name = "unidad_medida", length = 20)
    private String unidadMedida;

    @Column(name = "existencia_actual", precision = 12, scale = 3)
    private BigDecimal existenciaActual = BigDecimal.ZERO;

    @Column(name = "existencia_minima", precision = 12, scale = 3)
    private BigDecimal existenciaMinima = BigDecimal.ZERO;

    @Column(name = "existencia_maxima", precision = 12, scale = 3)
    private BigDecimal existenciaMaxima;

    @Column(name = "costo_promedio", precision = 12, scale = 2)
    private BigDecimal costoPromedio = BigDecimal.ZERO;

    @Column(name = "precio_venta", precision = 12, scale = 2)
    private BigDecimal precioVenta = BigDecimal.ZERO;

    @Column(name = "lote", length = 50)
    private String lote;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "ubicacion_almacen", length = 100)
    private String ubicacionAlmacen;

    @Column(name = "fecha_ultimo_movimiento")
    private LocalDateTime fechaUltimoMovimiento;

    @Column(name = "dias_sin_movimiento")
    private Integer diasSinMovimiento = 0;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    public enum TipoProducto {
        MATERIA_PRIMA, PRODUCTO_TERMINADO, MAQUINARIA, EQUIPO, VEHICULO
    }

    public Inventario() {
    }

    // Getters y Setters
    public Long getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Long idInventario) {
        this.idInventario = idInventario;
    }

    public CategoriaInventario getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaInventario categoria) {
        this.categoria = categoria;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNumeroParte() {
        return numeroParte;
    }

    public void setNumeroParte(String numeroParte) {
        this.numeroParte = numeroParte;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getExistenciaActual() {
        return existenciaActual;
    }

    public void setExistenciaActual(BigDecimal existenciaActual) {
        this.existenciaActual = existenciaActual;
    }

    public BigDecimal getExistenciaMinima() {
        return existenciaMinima;
    }

    public void setExistenciaMinima(BigDecimal existenciaMinima) {
        this.existenciaMinima = existenciaMinima;
    }

    public BigDecimal getExistenciaMaxima() {
        return existenciaMaxima;
    }

    public void setExistenciaMaxima(BigDecimal existenciaMaxima) {
        this.existenciaMaxima = existenciaMaxima;
    }

    public BigDecimal getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(BigDecimal costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getUbicacionAlmacen() {
        return ubicacionAlmacen;
    }

    public void setUbicacionAlmacen(String ubicacionAlmacen) {
        this.ubicacionAlmacen = ubicacionAlmacen;
    }

    public LocalDateTime getFechaUltimoMovimiento() {
        return fechaUltimoMovimiento;
    }

    public void setFechaUltimoMovimiento(LocalDateTime fechaUltimoMovimiento) {
        this.fechaUltimoMovimiento = fechaUltimoMovimiento;
    }

    public Integer getDiasSinMovimiento() {
        return diasSinMovimiento;
    }

    public void setDiasSinMovimiento(Integer diasSinMovimiento) {
        this.diasSinMovimiento = diasSinMovimiento;
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

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}