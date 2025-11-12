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

import com.proyecto.multigiros.entity.Remision;
import com.proyecto.multigiros.repository.RemisionRepository;

@RestController
@RequestMapping("/remision")
@CrossOrigin
public class RemisionService {
    @Autowired
    private RemisionRepository dbRemision;

    @GetMapping("/listar")
    public List<Remision> listar() {
        return dbRemision.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Remision> buscar(@PathVariable Long id) {
        return dbRemision.findById(id);
    }

    @GetMapping("/buscarpornumero/{numero}")
    public Optional<Remision> buscarpornumero(@PathVariable String numero) {
        return dbRemision.findByNumeroRemision(numero);
    }

    @GetMapping("/buscarpororden/{idOrden}")
    public List<Remision> buscarpororden(@PathVariable Long idOrden) {
        return dbRemision.findByOrdenIdOrden(idOrden);
    }

    @PostMapping("/insertar")
    public Remision insertar(@RequestBody Remision remision) {
        return dbRemision.save(remision);
    }

    @PutMapping("/modificar")
    public Remision modificar(@RequestBody Remision remision) {
        return dbRemision.save(remision);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbRemision.deleteById(id);
        return "Éxito: Remisión eliminada: " + id;
    }
}
