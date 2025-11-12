package com.proyecto.multigiros.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "monitoreo_gps")
public class MonitoreoGps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_monitoreo")
    private Long idMonitoreo;

    @ManyToOne
    @JoinColumn(name = "id_dispositivo", nullable = false)
    private DispositivoGps dispositivo;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora = LocalDateTime.now();

    @Column(name = "latitud", precision = 10, scale = 6)
    private BigDecimal latitud;

    @Column(name = "longitud", precision = 10, scale = 6)
    private BigDecimal longitud;

    @Column(name = "velocidad", precision = 8, scale = 2)
    private BigDecimal velocidad;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @Column(name = "estado_operativo", length = 50)
    private String estadoOperativo;

    @Column(name = "odometro", precision = 12, scale = 2)
    private BigDecimal odometro;

    @Column(name = "nivel_combustible", precision = 5, scale = 2)
    private BigDecimal nivelCombustible;

    @Column(name = "temperatura", precision = 5, scale = 2)
    private BigDecimal temperatura;

    public MonitoreoGps() {
    }

    // Getters y Setters
    public Long getIdMonitoreo() {
        return idMonitoreo;
    }

    public void setIdMonitoreo(Long idMonitoreo) {
        this.idMonitoreo = idMonitoreo;
    }

    public DispositivoGps getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(DispositivoGps dispositivo) {
        this.dispositivo = dispositivo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public BigDecimal getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(BigDecimal velocidad) {
        this.velocidad = velocidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstadoOperativo() {
        return estadoOperativo;
    }

    public void setEstadoOperativo(String estadoOperativo) {
        this.estadoOperativo = estadoOperativo;
    }

    public BigDecimal getOdometro() {
        return odometro;
    }

    public void setOdometro(BigDecimal odometro) {
        this.odometro = odometro;
    }

    public BigDecimal getNivelCombustible() {
        return nivelCombustible;
    }

    public void setNivelCombustible(BigDecimal nivelCombustible) {
        this.nivelCombustible = nivelCombustible;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
    }
}