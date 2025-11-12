package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.EstadoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository("EstadoEmpleadoRepository")
public interface EstadoEmpleadoRepository extends JpaRepository<EstadoEmpleado, Long> {
    Optional<EstadoEmpleado> findByEstado(String estado);
}