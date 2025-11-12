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

import com.proyecto.multigiros.entity.MetodoPago;
import com.proyecto.multigiros.repository.MetodoPagoRepository;

@RestController
@RequestMapping("/metodopago")
@CrossOrigin
public class MetodoPagoService {
    @Autowired
    private MetodoPagoRepository dbMetodoPago;

    @GetMapping("/listar")
    public List<MetodoPago> listar() {
        return dbMetodoPago.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<MetodoPago> buscar(@PathVariable Long id) {
        return dbMetodoPago.findById(id);
    }

    @GetMapping("/buscarpornombre/{nombre}")
    public Optional<MetodoPago> buscarpornombre(@PathVariable String nombre) {
        return dbMetodoPago.findByNombreMetodo(nombre);
    }

    @PostMapping("/insertar")
    public MetodoPago insertar(@RequestBody MetodoPago metodoPago) {
        return dbMetodoPago.save(metodoPago);
    }

    @PutMapping("/modificar")
    public MetodoPago modificar(@RequestBody MetodoPago metodoPago) {
        return dbMetodoPago.save(metodoPago);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbMetodoPago.deleteById(id);
        return "Éxito: Método pago eliminado: " + id;
    }
}
