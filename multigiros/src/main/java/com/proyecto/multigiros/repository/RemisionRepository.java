package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.Remision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("RemisionRepository")
public interface RemisionRepository extends JpaRepository<Remision, Long> {
    Optional<Remision> findByNumeroRemision(String numeroRemision);
    List<Remision> findByOrdenIdOrden(Long idOrden);
    List<Remision> findByEstado(Remision.EstadoRemision estado);
    List<Remision> findByDispositivoIdDispositivo(Long idDispositivo);
    List<Remision> findBySucursalOrigenIdSucursal(Long idSucursal);
    boolean existsByNumeroRemision(String numeroRemision);
}