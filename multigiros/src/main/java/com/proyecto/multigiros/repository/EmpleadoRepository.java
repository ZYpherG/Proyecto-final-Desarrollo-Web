package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("EmpleadoRepository")
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByPersonaIdPersona(Long idPersona);
    Optional<Empleado> findByCodigoEmpleado(String codigoEmpleado);
    List<Empleado> findByActivoTrue();
    List<Empleado> findBySucursalIdSucursal(Long idSucursal);
    List<Empleado> findByEstadoEmpleadoIdEstadoEmpleado(Long idEstadoEmpleado);
    boolean existsByCodigoEmpleado(String codigoEmpleado);
    boolean existsByPersonaIdPersona(Long idPersona);
}