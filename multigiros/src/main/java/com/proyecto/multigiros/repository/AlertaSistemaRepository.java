package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.AlertaSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository("AlertaSistemaRepository")
public interface AlertaSistemaRepository extends JpaRepository<AlertaSistema, Long> {
    List<AlertaSistema> findByTipoAlertaIdTipoAlerta(Long idTipoAlerta);
    List<AlertaSistema> findByUsuarioDestinoIdUsuario(Long idUsuario);
    List<AlertaSistema> findByGiroNegocioIdGiroNegocio(Long idGiroNegocio);
    List<AlertaSistema> findByDispositivoIdDispositivo(Long idDispositivo);
    List<AlertaSistema> findByOrdenIdOrden(Long idOrden);
    List<AlertaSistema> findByLeida(Boolean leida);
    List<AlertaSistema> findByPrioridad(AlertaSistema.Prioridad prioridad);
    List<AlertaSistema> findByFechaCreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}