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

import com.proyecto.multigiros.entity.GiroNegocio;
import com.proyecto.multigiros.repository.GiroNegocioRepository;

@RestController
@RequestMapping("/gironegocio")
@CrossOrigin
public class GiroNegocioService {
    @Autowired
    private GiroNegocioRepository dbGiroNegocio;

    @GetMapping("/listar")
    public List<GiroNegocio> listar() {
        return dbGiroNegocio.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<GiroNegocio> buscar(@PathVariable Long id) {
        return dbGiroNegocio.findById(id);
    }

    @GetMapping("/buscarporcodigo/{codigo}")
    public Optional<GiroNegocio> buscarporcodigo(@PathVariable String codigo) {
        return dbGiroNegocio.findByCodigoGiro(codigo);
    }

    @PostMapping("/insertar")
    public GiroNegocio insertar(@RequestBody GiroNegocio giro) {
        return dbGiroNegocio.save(giro);
    }

    @PutMapping("/modificar")
    public GiroNegocio modificar(@RequestBody GiroNegocio giro) {
        return dbGiroNegocio.save(giro);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbGiroNegocio.deleteById(id);
        return "Éxito: Giro de negocio eliminado: " + id;
    }

}
