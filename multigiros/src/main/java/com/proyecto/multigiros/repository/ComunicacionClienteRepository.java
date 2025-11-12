package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.ComunicacionCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository("ComunicacionClienteRepository")
public interface ComunicacionClienteRepository extends JpaRepository<ComunicacionCliente, Long> {
    List<ComunicacionCliente> findByClienteIdCliente(Long idCliente);
    List<ComunicacionCliente> findByUsuarioIdUsuario(Long idUsuario);
    List<ComunicacionCliente> findByOrdenIdOrden(Long idOrden);
    List<ComunicacionCliente> findByTipoComunicacion(ComunicacionCliente.TipoComunicacion tipoComunicacion);
    List<ComunicacionCliente> findByEstadoResuelto(Boolean estadoResuelto);
    List<ComunicacionCliente> findByFechaComunicacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}