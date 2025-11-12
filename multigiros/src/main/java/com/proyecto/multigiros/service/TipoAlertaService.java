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

import com.proyecto.multigiros.entity.TipoAlerta;
import com.proyecto.multigiros.repository.TipoAlertaRepository;

@RestController
@RequestMapping("/tipoalerta")
@CrossOrigin
public class TipoAlertaService {
     @Autowired
    private TipoAlertaRepository dbTipoAlerta;

    @GetMapping("/listar")
    public List<TipoAlerta> listar() {
        return dbTipoAlerta.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<TipoAlerta> buscar(@PathVariable Long id) {
        return dbTipoAlerta.findById(id);
    }

    @GetMapping("/buscarpornombre/{nombre}")
    public Optional<TipoAlerta> buscarpornombre(@PathVariable String nombre) {
        return dbTipoAlerta.findByNombreAlerta(nombre);
    }

    @PostMapping("/insertar")
    public TipoAlerta insertar(@RequestBody TipoAlerta tipoAlerta) {
        return dbTipoAlerta.save(tipoAlerta);
    }

    @PutMapping("/modificar")
    public TipoAlerta modificar(@RequestBody TipoAlerta tipoAlerta) {
        return dbTipoAlerta.save(tipoAlerta);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbTipoAlerta.deleteById(id);
        return "Éxito: Tipo alerta eliminado: " + id;
    }
}
