package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("ProveedorRepository")
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByPersonaIdPersona(Long idPersona);
    Optional<Proveedor> findByNombreComercial(String nombreComercial);
    List<Proveedor> findByActivoTrue();
    List<Proveedor> findByCategoriaProveedor(String categoriaProveedor);
    boolean existsByPersonaIdPersona(Long idPersona);
}