package com.proyecto.multigiros.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.multigiros.entity.GiroNegocio;

@Repository("GiroNegocioRepository")
public interface GiroNegocioRepository extends JpaRepository<GiroNegocio, Long>{
   Optional<GiroNegocio> findByCodigoGiro(String codigoGiro);
    List<GiroNegocio> findByActivoTrue();
    List<GiroNegocio> findByTipoGiro(GiroNegocio.TipoGiro tipoGiro);
    boolean existsByCodigoGiro(String codigoGiro); 
}
