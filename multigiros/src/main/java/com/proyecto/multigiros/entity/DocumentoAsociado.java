package com.proyecto.multigiros.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "documento_asociado")
public class DocumentoAsociado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Long idDocumento;
    
    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private Orden orden;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false, length = 50)
    private TipoDocumento tipoDocumento;
    
    @Column(name = "nombre_documento", nullable = false, length = 255)
    private String nombreDocumento;
    
    @Column(name = "ruta_documento", nullable = false, length = 500)
    private String rutaDocumento;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "fecha_carga")
    private LocalDateTime fechaCarga = LocalDateTime.now();
    
    @Column(name = "usuario_carga", length = 100)
    private String usuarioCarga;
    
    @Column(name = "tamaño_archivo")
    private Integer tamañoArchivo;
    
    @Column(name = "tipo_archivo", length = 20)
    private String tipoArchivo;
    
    public enum TipoDocumento {
        COTIZACION, PO, CONTRATO, INVOICE, REMESA, OTRO
    }
    
    public DocumentoAsociado() {}
    
    // Getters y Setters
    public Long getIdDocumento() { return idDocumento; }
    public void setIdDocumento(Long idDocumento) { this.idDocumento = idDocumento; }
    public Orden getOrden() { return orden; }
    public void setOrden(Orden orden) { this.orden = orden; }
    public TipoDocumento getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(TipoDocumento tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getNombreDocumento() { return nombreDocumento; }
    public void setNombreDocumento(String nombreDocumento) { this.nombreDocumento = nombreDocumento; }
    public String getRutaDocumento() { return rutaDocumento; }
    public void setRutaDocumento(String rutaDocumento) { this.rutaDocumento = rutaDocumento; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFechaCarga() { return fechaCarga; }
    public void setFechaCarga(LocalDateTime fechaCarga) { this.fechaCarga = fechaCarga; }
    public String getUsuarioCarga() { return usuarioCarga; }
    public void setUsuarioCarga(String usuarioCarga) { this.usuarioCarga = usuarioCarga; }
    public Integer getTamañoArchivo() { return tamañoArchivo; }
    public void setTamañoArchivo(Integer tamañoArchivo) { this.tamañoArchivo = tamañoArchivo; }
    public String getTipoArchivo() { return tipoArchivo; }
    public void setTipoArchivo(String tipoArchivo) { this.tipoArchivo = tipoArchivo; }
}