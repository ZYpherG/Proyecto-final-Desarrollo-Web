package com.proyecto.multigiros.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora")
public class Bitacora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bitacora")
    private Long idBitacora;
    
    @Column(name = "tabla", nullable = false, length = 100)
    private String tabla;
    
    @Column(name = "campo", length = 100)
    private String campo;
    
    @Column(name = "llave_primaria", length = 100)
    private String llavePrimaria;
    
    @Column(name = "valor_anterior", length = 500)
    private String valorAnterior;
    
    @Column(name = "valor_nuevo", length = 500)
    private String valorNuevo;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "usuario_creacion", length = 100)
    private String usuarioCreacion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false, length = 40)
    private TipoMovimiento tipoMovimiento;
    
    @Column(name = "ip_registro", length = 50)
    private String ipRegistro;
    
    public enum TipoMovimiento {
        CREACION, MODIFICACION, ELIMINACION
    }
    
    public Bitacora() {}
    
    // Constructores adicionales para facilitar el uso
    public Bitacora(String tabla, String llavePrimaria, TipoMovimiento tipoMovimiento, String usuarioCreacion) {
        this.tabla = tabla;
        this.llavePrimaria = llavePrimaria;
        this.tipoMovimiento = tipoMovimiento;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public Bitacora(String tabla, String campo, String llavePrimaria, String valorAnterior, 
                   String valorNuevo, TipoMovimiento tipoMovimiento, String usuarioCreacion) {
        this.tabla = tabla;
        this.campo = campo;
        this.llavePrimaria = llavePrimaria;
        this.valorAnterior = valorAnterior;
        this.valorNuevo = valorNuevo;
        this.tipoMovimiento = tipoMovimiento;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getIdBitacora() { return idBitacora; }
    public void setIdBitacora(Long idBitacora) { this.idBitacora = idBitacora; }
    public String getTabla() { return tabla; }
    public void setTabla(String tabla) { this.tabla = tabla; }
    public String getCampo() { return campo; }
    public void setCampo(String campo) { this.campo = campo; }
    public String getLlavePrimaria() { return llavePrimaria; }
    public void setLlavePrimaria(String llavePrimaria) { this.llavePrimaria = llavePrimaria; }
    public String getValorAnterior() { return valorAnterior; }
    public void setValorAnterior(String valorAnterior) { this.valorAnterior = valorAnterior; }
    public String getValorNuevo() { return valorNuevo; }
    public void setValorNuevo(String valorNuevo) { this.valorNuevo = valorNuevo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public String getUsuarioCreacion() { return usuarioCreacion; }
    public void setUsuarioCreacion(String usuarioCreacion) { this.usuarioCreacion = usuarioCreacion; }
    public TipoMovimiento getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }
    public String getIpRegistro() { return ipRegistro; }
    public void setIpRegistro(String ipRegistro) { this.ipRegistro = ipRegistro; }
    
    @Override
    public String toString() {
        return "Bitacora{" +
                "idBitacora=" + idBitacora +
                ", tabla='" + tabla + '\'' +
                ", campo='" + campo + '\'' +
                ", llavePrimaria='" + llavePrimaria + '\'' +
                ", tipoMovimiento=" + tipoMovimiento +
                ", usuarioCreacion='" + usuarioCreacion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}