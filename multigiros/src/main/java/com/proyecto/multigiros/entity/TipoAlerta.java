package com.proyecto.multigiros.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_alerta")
public class TipoAlerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_alerta")
    private Long idTipoAlerta;
    
    @Column(name = "nombre_alerta", nullable = false, unique = true, length = 100)
    private String nombreAlerta;
    
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", length = 20)
    private Prioridad prioridad = Prioridad.MEDIA;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    public enum Prioridad {
        BAJA, MEDIA, ALTA, URGENTE
    }
    
    public TipoAlerta() {}
    
    public Long getIdTipoAlerta() { return idTipoAlerta; }
    public void setIdTipoAlerta(Long idTipoAlerta) { this.idTipoAlerta = idTipoAlerta; }
    public String getNombreAlerta() { return nombreAlerta; }
    public void setNombreAlerta(String nombreAlerta) { this.nombreAlerta = nombreAlerta; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}