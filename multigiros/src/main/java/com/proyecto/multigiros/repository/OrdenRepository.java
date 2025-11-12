package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("OrdenRepository")
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    Optional<Orden> findByNumeroOrden(String numeroOrden);
    List<Orden> findByTipoOrden(Orden.TipoOrden tipoOrden);
    List<Orden> findByEstadoOrdenIdEstadoOrden(Long idEstadoOrden);
    List<Orden> findByClienteIdCliente(Long idCliente);
    List<Orden> findByProveedorIdProveedor(Long idProveedor);
    List<Orden> findByFechaCreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Orden> findByFechaRequeridaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    List<Orden> findByPrioridad(Orden.Prioridad prioridad);
    boolean existsByNumeroOrden(String numeroOrden);
    @Query("SELECT o FROM Orden o WHERE o.giroNegocioEjecutor.idGiroNegocio = :idGiroNegocio AND o.fechaRequerida < CURRENT_DATE AND o.estadoOrden.estado != 'COMPLETADA'")
List<Orden> findOrdenesAtrasadasByGiroNegocio(@Param("idGiroNegocio") Long idGiroNegocio);

@Query("SELECT o.tipoOrden, COUNT(o), SUM(o.total) FROM Orden o WHERE o.giroNegocioEjecutor.idGiroNegocio = :idGiroNegocio GROUP BY o.tipoOrden")
List<Object[]> findEstadisticasByGiroNegocio(@Param("idGiroNegocio") Long idGiroNegocio);
}