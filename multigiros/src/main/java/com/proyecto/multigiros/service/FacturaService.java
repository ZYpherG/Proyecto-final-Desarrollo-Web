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

import com.proyecto.multigiros.entity.Factura;
import com.proyecto.multigiros.repository.FacturaRepository;

@RestController
@RequestMapping("/factura")
@CrossOrigin
public class FacturaService {
    @Autowired
    private FacturaRepository dbFactura;

    @GetMapping("/listar")
    public List<Factura> listar() {
        return dbFactura.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Factura> buscar(@PathVariable Long id) {
        return dbFactura.findById(id);
    }

    @GetMapping("/buscarpornumero/{numero}")
    public Optional<Factura> buscarpornumero(@PathVariable String numero) {
        return dbFactura.findByNumeroFactura(numero);
    }

    @GetMapping("/buscarpororden/{idOrden}")
    public List<Factura> buscarpororden(@PathVariable Long idOrden) {
        return dbFactura.findByOrdenIdOrden(idOrden);
    }

    @PostMapping("/insertar")
    public Factura insertar(@RequestBody Factura factura) {
        return dbFactura.save(factura);
    }

    @PutMapping("/modificar")
    public Factura modificar(@RequestBody Factura factura) {
        return dbFactura.save(factura);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbFactura.deleteById(id);
        return "Éxito: Factura eliminada: " + id;
    }
}
