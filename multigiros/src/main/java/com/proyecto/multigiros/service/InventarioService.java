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

import com.proyecto.multigiros.entity.Inventario;
import com.proyecto.multigiros.repository.InventarioRepository;

@RestController
@RequestMapping("/inventario")
@CrossOrigin
public class InventarioService {
     @Autowired
    private InventarioRepository dbInventario;

    @GetMapping("/listar")
    public List<Inventario> listar() {
        return dbInventario.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Inventario> buscar(@PathVariable Long id) {
        return dbInventario.findById(id);
    }

    @GetMapping("/buscarporcodigo/{codigo}")
    public Optional<Inventario> buscarporcodigo(@PathVariable String codigo) {
        return dbInventario.findByCodigoProducto(codigo);
    }

    @PostMapping("/insertar")
    public Inventario insertar(@RequestBody Inventario inventario) {
        return dbInventario.save(inventario);
    }

    @PutMapping("/modificar")
    public Inventario modificar(@RequestBody Inventario inventario) {
        return dbInventario.save(inventario);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbInventario.deleteById(id);
        return "Éxito: Inventario eliminado: " + id;
    }

}
