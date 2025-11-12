package com.proyecto.multigiros.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerta_sistema")
public class AlertaSistema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta_sistema")
    private Long idAlertaSistema;
    
    @ManyToOne
    @JoinColumn(name = "id_tipo_alerta", nullable = false)
    private TipoAlerta tipoAlerta;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario_destino")
    private Usuario usuarioDestino;
    
    @ManyToOne
    @JoinColumn(name = "id_giro_negocio")
    private GiroNegocio giroNegocio;
    
    @ManyToOne
    @JoinColumn(name = "id_dispositivo")
    private DispositivoGps dispositivo;
    
    @ManyToOne
    @JoinColumn(name = "id_orden")
    private Orden orden;
    
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "fecha_leida")
    private LocalDateTime fechaLeida;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", length = 20)
    private Prioridad prioridad = Prioridad.MEDIA;
    
    @Column(name = "leida")
    private Boolean leida = false;
    
    @Column(name = "accion_requerida", length = 100)
    private String accionRequerida;
    
    public enum Prioridad {
        BAJA, MEDIA, ALTA, URGENTE
    }
    
    public AlertaSistema() {}
    
    // Getters y Setters
    public Long getIdAlertaSistema() { return idAlertaSistema; }
    public void setIdAlertaSistema(Long idAlertaSistema) { this.idAlertaSistema = idAlertaSistema; }
    public TipoAlerta getTipoAlerta() { return tipoAlerta; }
    public void setTipoAlerta(TipoAlerta tipoAlerta) { this.tipoAlerta = tipoAlerta; }
    public Usuario getUsuarioDestino() { return usuarioDestino; }
    public void setUsuarioDestino(Usuario usuarioDestino) { this.usuarioDestino = usuarioDestino; }
    public GiroNegocio getGiroNegocio() { return giroNegocio; }
    public void setGiroNegocio(GiroNegocio giroNegocio) { this.giroNegocio = giroNegocio; }
    public DispositivoGps getDispositivo() { return dispositivo; }
    public void setDispositivo(DispositivoGps dispositivo) { this.dispositivo = dispositivo; }
    public Orden getOrden() { return orden; }
    public void setOrden(Orden orden) { this.orden = orden; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaLeida() { return fechaLeida; }
    public void setFechaLeida(LocalDateTime fechaLeida) { this.fechaLeida = fechaLeida; }
    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }
    public Boolean getLeida() { return leida; }
    public void setLeida(Boolean leida) { this.leida = leida; }
    public String getAccionRequerida() { return accionRequerida; }
    public void setAccionRequerida(String accionRequerida) { this.accionRequerida = accionRequerida; }
}