package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository("BitacoraRepository")
public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {
    List<Bitacora> findByTabla(String tabla);
    List<Bitacora> findByTipoMovimiento(Bitacora.TipoMovimiento tipoMovimiento);
    List<Bitacora> findByUsuarioCreacion(String usuarioCreacion);
    List<Bitacora> findByFechaCreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    @Query("SELECT b FROM Bitacora b WHERE b.tabla = :tabla AND b.fechaCreacion BETWEEN :fechaInicio AND :fechaFin")
    List<Bitacora> findByTablaAndFechaBetween(@Param("tabla") String tabla, 
                                            @Param("fechaInicio") LocalDateTime fechaInicio, 
                                            @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT b FROM Bitacora b WHERE b.usuarioCreacion = :usuario AND b.fechaCreacion BETWEEN :fechaInicio AND :fechaFin")
    List<Bitacora> findByUsuarioAndFechaBetween(@Param("usuario") String usuario, 
                                              @Param("fechaInicio") LocalDateTime fechaInicio, 
                                              @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT DISTINCT b.tabla FROM Bitacora b")
    List<String> findDistinctTablas();
    
    @Query("SELECT DISTINCT b.usuarioCreacion FROM Bitacora b WHERE b.usuarioCreacion IS NOT NULL")
    List<String> findDistinctUsuarios();
}