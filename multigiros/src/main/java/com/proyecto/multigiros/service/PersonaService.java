package com.proyecto.multigiros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.multigiros.entity.Persona;
import com.proyecto.multigiros.repository.PersonaRepository;

@RestController
@RequestMapping("/persona")
@CrossOrigin
public class PersonaService {
    @Autowired
    private PersonaRepository dbPersona;

    @GetMapping("/listar")
    public List<Persona> listar() {
        return dbPersona.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Persona> buscar(@PathVariable Long id) {
        return dbPersona.findById(id);
    }

    @GetMapping("/buscarpordocumento/{documento}")
    public Optional<Persona> buscarpordocumento(@PathVariable String documento) {
        return dbPersona.findByNumeroDocumento(documento);
    }

    @PostMapping("/insertar")
    public Persona insertar(@RequestBody Persona persona) {
        return dbPersona.save(persona);
    }

    @PutMapping("/modificar")
    public Persona modificar(@RequestBody Persona persona) {
        return dbPersona.save(persona);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbPersona.deleteById(id);
        return "Éxito: Persona eliminada: " + id;
    }
}
