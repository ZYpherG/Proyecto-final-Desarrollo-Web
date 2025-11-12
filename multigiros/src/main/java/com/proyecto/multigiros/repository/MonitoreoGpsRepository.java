package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.MonitoreoGps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository("MonitoreoGpsRepository")
public interface MonitoreoGpsRepository extends JpaRepository<MonitoreoGps, Long> {
    List<MonitoreoGps> findByDispositivoIdDispositivo(Long idDispositivo);
    List<MonitoreoGps> findByFechaHoraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<MonitoreoGps> findByDispositivoIdDispositivoAndFechaHoraBetween(Long idDispositivo, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}