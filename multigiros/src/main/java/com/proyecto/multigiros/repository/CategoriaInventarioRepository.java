package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.CategoriaInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("CategoriaInventarioRepository")
public interface CategoriaInventarioRepository extends JpaRepository<CategoriaInventario, Long> {
    List<CategoriaInventario> findByGiroNegocioIdGiroNegocio(Long idGiroNegocio);
    List<CategoriaInventario> findByActivoTrue();
    Optional<CategoriaInventario> findByNombreCategoriaAndGiroNegocioIdGiroNegocio(String nombreCategoria, Long idGiroNegocio);
}