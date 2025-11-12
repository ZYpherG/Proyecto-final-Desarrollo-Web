package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository("MovimientoInventarioRepository")
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
    List<MovimientoInventario> findByInventarioIdInventario(Long idInventario);
    List<MovimientoInventario> findByTipoMovimiento(MovimientoInventario.TipoMovimiento tipoMovimiento);
    List<MovimientoInventario> findByOrdenIdOrden(Long idOrden);
    List<MovimientoInventario> findByUsuarioIdUsuario(Long idUsuario);
    List<MovimientoInventario> findByFechaMovimientoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}