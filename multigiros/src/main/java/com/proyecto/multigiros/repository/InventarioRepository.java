package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("InventarioRepository")
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByCodigoProducto(String codigoProducto);
    Optional<Inventario> findByCodigoBarras(String codigoBarras);
    List<Inventario> findByActivoTrue();
    List<Inventario> findBySucursalIdSucursal(Long idSucursal);
    List<Inventario> findByCategoriaIdCategoria(Long idCategoria);
    List<Inventario> findByTipoProducto(Inventario.TipoProducto tipoProducto);
    List<Inventario> findByExistenciaActualLessThanEqual(Double existenciaMinima);
    boolean existsByCodigoProducto(String codigoProducto);
    boolean existsByCodigoBarras(String codigoBarras);
}