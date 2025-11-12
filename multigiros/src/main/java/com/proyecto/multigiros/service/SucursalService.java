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

import com.proyecto.multigiros.entity.Sucursal;
import com.proyecto.multigiros.repository.SucursalRepository;

@RestController
@RequestMapping("/sucursal")
@CrossOrigin
public class SucursalService {
    @Autowired
    private SucursalRepository dbSucursal;

    @GetMapping("/listar")
    public List<Sucursal> listar() {
        return dbSucursal.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Sucursal> buscar(@PathVariable Long id) {
        return dbSucursal.findById(id);
    }

    @GetMapping("/buscarporcodigo/{codigo}")
    public Optional<Sucursal> buscarporcodigo(@PathVariable String codigo) {
        return dbSucursal.findByCodigoSucursal(codigo);
    }

    @GetMapping("/buscarporgiro/{idGiroNegocio}")
    public List<Sucursal> buscarporgiro(@PathVariable Long idGiroNegocio) {
        return dbSucursal.findByGiroNegocioIdGiroNegocio(idGiroNegocio);
    }

    @PostMapping("/insertar")
    public Sucursal insertar(@RequestBody Sucursal sucursal) {
        return dbSucursal.save(sucursal);
    }

    @PutMapping("/modificar")
    public Sucursal modificar(@RequestBody Sucursal sucursal) {
        return dbSucursal.save(sucursal);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbSucursal.deleteById(id);
        return "Éxito: Sucursal eliminada: " + id;
    }

}
