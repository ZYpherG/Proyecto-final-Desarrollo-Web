package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("DetalleOrdenRepository")
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
    List<DetalleOrden> findByOrdenIdOrden(Long idOrden);
    List<DetalleOrden> findByInventarioIdInventario(Long idInventario);
    List<DetalleOrden> findByEstadoDetalle(DetalleOrden.EstadoDetalle estadoDetalle);
}