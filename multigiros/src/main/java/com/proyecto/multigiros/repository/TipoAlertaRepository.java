package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.TipoAlerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("TipoAlertaRepository")
public interface TipoAlertaRepository extends JpaRepository<TipoAlerta, Long> {
    Optional<TipoAlerta> findByNombreAlerta(String nombreAlerta);
    List<TipoAlerta> findByActivoTrue();
    List<TipoAlerta> findByPrioridad(TipoAlerta.Prioridad prioridad);
}