package com.proyecto.multigiros.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comunicacion_cliente")
public class ComunicacionCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comunicacion")
    private Long idComunicacion;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "id_orden")
    private Orden orden;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_comunicacion", nullable = false, length = 30)
    private TipoComunicacion tipoComunicacion;
    
    @Column(name = "asunto", length = 200)
    private String asunto;
    
    @Column(name = "contenido", columnDefinition = "TEXT")
    private String contenido;
    
    @Column(name = "archivo_adjunto", length = 500)
    private String archivoAdjunto;
    
    @Column(name = "ruta_archivo", length = 500)
    private String rutaArchivo;
    
    @Column(name = "fecha_comunicacion")
    private LocalDateTime fechaComunicacion = LocalDateTime.now();
    
    @Column(name = "estado_resuelto")
    private Boolean estadoResuelto = false;
    
    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;
    
    public enum TipoComunicacion {
        EMAIL, LLAMADA, MENSAJE, REUNION, VIDEO_LLAMADA
    }
    
    public ComunicacionCliente() {}
    
    // Getters y Setters
    public Long getIdComunicacion() { return idComunicacion; }
    public void setIdComunicacion(Long idComunicacion) { this.idComunicacion = idComunicacion; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Orden getOrden() { return orden; }
    public void setOrden(Orden orden) { this.orden = orden; }
    public TipoComunicacion getTipoComunicacion() { return tipoComunicacion; }
    public void setTipoComunicacion(TipoComunicacion tipoComunicacion) { this.tipoComunicacion = tipoComunicacion; }
    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public String getArchivoAdjunto() { return archivoAdjunto; }
    public void setArchivoAdjunto(String archivoAdjunto) { this.archivoAdjunto = archivoAdjunto; }
    public String getRutaArchivo() { return rutaArchivo; }
    public void setRutaArchivo(String rutaArchivo) { this.rutaArchivo = rutaArchivo; }
    public LocalDateTime getFechaComunicacion() { return fechaComunicacion; }
    public void setFechaComunicacion(LocalDateTime fechaComunicacion) { this.fechaComunicacion = fechaComunicacion; }
    public Boolean getEstadoResuelto() { return estadoResuelto; }
    public void setEstadoResuelto(Boolean estadoResuelto) { this.estadoResuelto = estadoResuelto; }
    public LocalDateTime getFechaResolucion() { return fechaResolucion; }
    public void setFechaResolucion(LocalDateTime fechaResolucion) { this.fechaResolucion = fechaResolucion; }
}