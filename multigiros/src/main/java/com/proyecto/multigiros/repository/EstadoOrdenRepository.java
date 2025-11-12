package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("EstadoOrdenRepository")
public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, Long> {
    Optional<EstadoOrden> findByEstado(String estado);
    List<EstadoOrden> findByEsFinal(Boolean esFinal);
}