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

import com.proyecto.multigiros.entity.ComunicacionCliente;
import com.proyecto.multigiros.repository.ComunicacionClienteRepository;

@RestController
@RequestMapping("/comunicacioncliente")
@CrossOrigin
public class ComunicacionClienteService {
    @Autowired
    private ComunicacionClienteRepository dbComunicacionCliente;

    @GetMapping("/listar")
    public List<ComunicacionCliente> listar() {
        return dbComunicacionCliente.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<ComunicacionCliente> buscar(@PathVariable Long id) {
        return dbComunicacionCliente.findById(id);
    }

    @GetMapping("/buscarporcliente/{idCliente}")
    public List<ComunicacionCliente> buscarporcliente(@PathVariable Long idCliente) {
        return dbComunicacionCliente.findByClienteIdCliente(idCliente);
    }

    @PostMapping("/insertar")
    public ComunicacionCliente insertar(@RequestBody ComunicacionCliente comunicacion) {
        return dbComunicacionCliente.save(comunicacion);
    }

    @PutMapping("/modificar")
    public ComunicacionCliente modificar(@RequestBody ComunicacionCliente comunicacion) {
        return dbComunicacionCliente.save(comunicacion);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbComunicacionCliente.deleteById(id);
        return "Éxito: Comunicación cliente eliminada: " + id;
    }
}
