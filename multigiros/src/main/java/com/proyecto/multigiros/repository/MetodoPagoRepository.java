package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("MetodoPagoRepository")
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
    Optional<MetodoPago> findByNombreMetodo(String nombreMetodo);
    List<MetodoPago> findByActivoTrue();
}