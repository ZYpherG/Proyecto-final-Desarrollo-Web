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

import com.proyecto.multigiros.entity.Orden;
import com.proyecto.multigiros.repository.OrdenRepository;

@RestController
@RequestMapping("/orden")
@CrossOrigin
public class OrdenService {
     @Autowired
    private OrdenRepository dbOrden;

    @GetMapping("/listar")
    public List<Orden> listar() {
        return dbOrden.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Orden> buscar(@PathVariable Long id) {
        return dbOrden.findById(id);
    }

    @GetMapping("/buscarpornumero/{numero}")
    public Optional<Orden> buscarpornumero(@PathVariable String numero) {
        return dbOrden.findByNumeroOrden(numero);
    }

    @PostMapping("/insertar")
    public Orden insertar(@RequestBody Orden orden) {
        return dbOrden.save(orden);
    }

    @PutMapping("/modificar")
    public Orden modificar(@RequestBody Orden orden) {
        return dbOrden.save(orden);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbOrden.deleteById(id);
        return "Éxito: Orden eliminada: " + id;
    }
}
