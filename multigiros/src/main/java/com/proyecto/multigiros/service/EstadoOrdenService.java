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

import com.proyecto.multigiros.entity.EstadoOrden;
import com.proyecto.multigiros.repository.EstadoOrdenRepository;

@RestController
@RequestMapping("/estadoorden")
@CrossOrigin
public class EstadoOrdenService {
    @Autowired
    private EstadoOrdenRepository dbEstadoOrden;

    @GetMapping("/listar")
    public List<EstadoOrden> listar() {
        return dbEstadoOrden.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<EstadoOrden> buscar(@PathVariable Long id) {
        return dbEstadoOrden.findById(id);
    }

    @GetMapping("/buscarpornombre/{nombre}")
    public Optional<EstadoOrden> buscarpornombre(@PathVariable String nombre) {
        return dbEstadoOrden.findByEstado(nombre);
    }

    @PostMapping("/insertar")
    public EstadoOrden insertar(@RequestBody EstadoOrden estado) {
        return dbEstadoOrden.save(estado);
    }

    @PutMapping("/modificar")
    public EstadoOrden modificar(@RequestBody EstadoOrden estado) {
        return dbEstadoOrden.save(estado);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbEstadoOrden.deleteById(id);
        return "Éxito: Estado orden eliminado: " + id;
    }

}
