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

import com.proyecto.multigiros.entity.DetalleOrden;
import com.proyecto.multigiros.repository.DetalleOrdenRepository;

@RestController
@RequestMapping("/detalleorden")
@CrossOrigin
public class DetalleOrdenService {
    @Autowired
    private DetalleOrdenRepository dbDetalleOrden;

    @GetMapping("/listar")
    public List<DetalleOrden> listar() {
        return dbDetalleOrden.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<DetalleOrden> buscar(@PathVariable Long id) {
        return dbDetalleOrden.findById(id);
    }

    @GetMapping("/buscarpororden/{idOrden}")
    public List<DetalleOrden> buscarpororden(@PathVariable Long idOrden) {
        return dbDetalleOrden.findByOrdenIdOrden(idOrden);
    }

    @PostMapping("/insertar")
    public DetalleOrden insertar(@RequestBody DetalleOrden detalle) {
        return dbDetalleOrden.save(detalle);
    }

    @PutMapping("/modificar")
    public DetalleOrden modificar(@RequestBody DetalleOrden detalle) {
        return dbDetalleOrden.save(detalle);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbDetalleOrden.deleteById(id);
        return "Éxito: Detalle orden eliminado: " + id;
    }
}
