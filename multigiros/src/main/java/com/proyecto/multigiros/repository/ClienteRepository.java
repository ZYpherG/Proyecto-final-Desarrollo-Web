package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("ClienteRepository")
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByPersonaIdPersona(Long idPersona);
    List<Cliente> findByActivoTrue();
    List<Cliente> findByCategoria(Cliente.CategoriaCliente categoria);
    boolean existsByPersonaIdPersona(Long idPersona);
}