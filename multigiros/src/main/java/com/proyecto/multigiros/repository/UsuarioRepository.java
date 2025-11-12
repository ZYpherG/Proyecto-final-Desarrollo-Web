package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("UsuarioRepository")
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByActivoTrue();
    List<Usuario> findByRol(Usuario.RolUsuario rol);
    List<Usuario> findByGiroNegocioIdGiroNegocio(Long idGiroNegocio);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
    
    @Modifying
    @Query("UPDATE Usuario u SET u.fechaUltimoLogin = :fechaLogin WHERE u.idUsuario = :idUsuario")
    void actualizarUltimoLogin(@Param("idUsuario") Long idUsuario, @Param("fechaLogin") LocalDateTime fechaLogin);
    
    @Modifying
    @Query("UPDATE Usuario u SET u.intentosFallidos = :intentos WHERE u.idUsuario = :idUsuario")
    void actualizarIntentosFallidos(@Param("idUsuario") Long idUsuario, @Param("intentos") Integer intentos);
    
    @Modifying
    @Query("UPDATE Usuario u SET u.bloqueado = :bloqueado WHERE u.idUsuario = :idUsuario")
    void actualizarBloqueo(@Param("idUsuario") Long idUsuario, @Param("bloqueado") Boolean bloqueado);
}