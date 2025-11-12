package com.proyecto.multigiros.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dispositivo_gps")
public class DispositivoGps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dispositivo")
    private Long idDispositivo;

    @ManyToOne
    @JoinColumn(name = "id_inventario", nullable = false)
    private Inventario inventario;

    @Column(name = "codigo_dispositivo", unique = true, nullable = false, length = 50)
    private String codigoDispositivo;

    @Column(name = "placa_vehiculo", unique = true, length = 20)
    private String placaVehiculo;

    @Column(name = "marca_modelo", length = 100)
    private String marcaModelo;

    @Column(name = "numero_motor", length = 50)
    private String numeroMotor;

    @Column(name = "numero_chasis", length = 50)
    private String numeroChasis;

    @Column(name = "capacidad_carga", precision = 12, scale = 2)
    private BigDecimal capacidadCarga;

    @Column(name = "fecha_ultimo_mantenimiento")
    private LocalDate fechaUltimoMantenimiento;

    @Column(name = "proximo_mantenimiento")
    private LocalDate proximoMantenimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_vehiculo", length = 50)
    private EstadoVehiculo estadoVehiculo = EstadoVehiculo.OPERATIVO;

    @Column(name = "proveedor_gps", length = 50)
    private String proveedorGps;

    @Column(name = "sim_card", length = 50)
    private String simCard;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public enum EstadoVehiculo {
        OPERATIVO, MANTENIMIENTO, INACTIVO
    }

    public DispositivoGps() {
    }

    public Long getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Long idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public String getCodigoDispositivo() {
        return codigoDispositivo;
    }

    public void setCodigoDispositivo(String codigoDispositivo) {
        this.codigoDispositivo = codigoDispositivo;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getMarcaModelo() {
        return marcaModelo;
    }

    public void setMarcaModelo(String marcaModelo) {
        this.marcaModelo = marcaModelo;
    }

    public String getNumeroMotor() {
        return numeroMotor;
    }

    public void setNumeroMotor(String numeroMotor) {
        this.numeroMotor = numeroMotor;
    }

    public String getNumeroChasis() {
        return numeroChasis;
    }

    public void setNumeroChasis(String numeroChasis) {
        this.numeroChasis = numeroChasis;
    }

    public BigDecimal getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(BigDecimal capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public LocalDate getFechaUltimoMantenimiento() {
        return fechaUltimoMantenimiento;
    }

    public void setFechaUltimoMantenimiento(LocalDate fechaUltimoMantenimiento) {
        this.fechaUltimoMantenimiento = fechaUltimoMantenimiento;
    }

    public LocalDate getProximoMantenimiento() {
        return proximoMantenimiento;
    }

    public void setProximoMantenimiento(LocalDate proximoMantenimiento) {
        this.proximoMantenimiento = proximoMantenimiento;
    }

    public EstadoVehiculo getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public void setEstadoVehiculo(EstadoVehiculo estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }

    public String getProveedorGps() {
        return proveedorGps;
    }

    public void setProveedorGps(String proveedorGps) {
        this.proveedorGps = proveedorGps;
    }

    public String getSimCard() {
        return simCard;
    }

    public void setSimCard(String simCard) {
        this.simCard = simCard;
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
