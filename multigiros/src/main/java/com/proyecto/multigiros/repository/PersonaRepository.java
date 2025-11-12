package com.proyecto.multigiros.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.multigiros.entity.Persona;

@Repository("PersonaRepository")
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByNumeroDocumento(String numeroDocumento);
    Optional<Persona> findByEmail(String email);
    boolean existsByNumeroDocumento(String numeroDocumento);
    boolean existsByEmail(String email);
}
