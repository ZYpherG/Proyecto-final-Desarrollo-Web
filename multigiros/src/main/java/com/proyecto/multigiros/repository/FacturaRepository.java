package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository("FacturaRepository")
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByNumeroFactura(String numeroFactura);
    List<Factura> findByOrdenIdOrden(Long idOrden);
    List<Factura> findByEstado(Factura.EstadoFactura estado);
    List<Factura> findByFechaEmisionBetween(LocalDate fechaInicio, LocalDate fechaFin);
    List<Factura> findByFechaVencimientoBefore(LocalDate fecha);
    boolean existsByNumeroFactura(String numeroFactura);
}