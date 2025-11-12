package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.DispositivoGps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("DispositivoGpsRepository")
public interface DispositivoGpsRepository extends JpaRepository<DispositivoGps, Long> {
    Optional<DispositivoGps> findByCodigoDispositivo(String codigoDispositivo);
    Optional<DispositivoGps> findByPlacaVehiculo(String placaVehiculo);
    List<DispositivoGps> findByActivoTrue();
    List<DispositivoGps> findByEstadoVehiculo(DispositivoGps.EstadoVehiculo estadoVehiculo);
    boolean existsByCodigoDispositivo(String codigoDispositivo);
    boolean existsByPlacaVehiculo(String placaVehiculo);
}