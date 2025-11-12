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

import com.proyecto.multigiros.entity.Bitacora;
import com.proyecto.multigiros.repository.BitacoraRepository;

@RestController
@RequestMapping("/bitacora")
@CrossOrigin
public class BitacoraService {
    @Autowired
    private BitacoraRepository dbBitacora;

    @GetMapping("/listar")
    public List<Bitacora> listar() {
        return dbBitacora.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Bitacora> buscar(@PathVariable Long id) {
        return dbBitacora.findById(id);
    }

    @GetMapping("/buscarportabla/{tabla}")
    public List<Bitacora> buscarportabla(@PathVariable String tabla) {
        return dbBitacora.findByTabla(tabla);
    }

    @PostMapping("/insertar")
    public Bitacora insertar(@RequestBody Bitacora bitacora) {
        return dbBitacora.save(bitacora);
    }

    @PutMapping("/modificar")
    public Bitacora modificar(@RequestBody Bitacora bitacora) {
        return dbBitacora.save(bitacora);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbBitacora.deleteById(id);
        return "Éxito: Bitácora eliminada: " + id;
    }
}
