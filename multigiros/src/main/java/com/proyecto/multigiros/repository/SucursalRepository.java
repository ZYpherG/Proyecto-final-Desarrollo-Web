package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("SucursalRepository")
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    Optional<Sucursal> findByCodigoSucursal(String codigoSucursal);
    List<Sucursal> findByActivaTrue();
    List<Sucursal> findByGiroNegocioIdGiroNegocio(Long idGiroNegocio);
    List<Sucursal> findByDepartamento(String departamento);
    boolean existsByCodigoSucursal(String codigoSucursal);
}