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

import com.proyecto.multigiros.entity.MovimientoInventario;
import com.proyecto.multigiros.repository.MovimientoInventarioRepository;

@RestController
@RequestMapping("/movimientoinventario")
@CrossOrigin
public class MovimientoInventarioService {
    @Autowired
    private MovimientoInventarioRepository dbMovimientoInventario;

    @GetMapping("/listar")
    public List<MovimientoInventario> listar() {
        return dbMovimientoInventario.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<MovimientoInventario> buscar(@PathVariable Long id) {
        return dbMovimientoInventario.findById(id);
    }

    @GetMapping("/buscarporinventario/{idInventario}")
    public List<MovimientoInventario> buscarporinventario(@PathVariable Long idInventario) {
        return dbMovimientoInventario.findByInventarioIdInventario(idInventario);
    }

    @PostMapping("/insertar")
    public MovimientoInventario insertar(@RequestBody MovimientoInventario movimiento) {
        return dbMovimientoInventario.save(movimiento);
    }

    @PutMapping("/modificar")
    public MovimientoInventario modificar(@RequestBody MovimientoInventario movimiento) {
        return dbMovimientoInventario.save(movimiento);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbMovimientoInventario.deleteById(id);
        return "Éxito: Movimiento inventario eliminado: " + id;
    }
}
