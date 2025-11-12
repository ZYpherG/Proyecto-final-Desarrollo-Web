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

import com.proyecto.multigiros.entity.CategoriaInventario;
import com.proyecto.multigiros.repository.CategoriaInventarioRepository;

@RestController
@RequestMapping("/categoriainventario")
@CrossOrigin
public class CategoriaInventarioService {
    @Autowired
    private CategoriaInventarioRepository dbCategoriaInventario;

    @GetMapping("/listar")
    public List<CategoriaInventario> listar() {
        return dbCategoriaInventario.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<CategoriaInventario> buscar(@PathVariable Long id) {
        return dbCategoriaInventario.findById(id);
    }

    @GetMapping("/buscarporgiro/{idGiroNegocio}")
    public List<CategoriaInventario> buscarporgiro(@PathVariable Long idGiroNegocio) {
        return dbCategoriaInventario.findByGiroNegocioIdGiroNegocio(idGiroNegocio);
    }

    @PostMapping("/insertar")
    public CategoriaInventario insertar(@RequestBody CategoriaInventario categoria) {
        return dbCategoriaInventario.save(categoria);
    }

    @PutMapping("/modificar")
    public CategoriaInventario modificar(@RequestBody CategoriaInventario categoria) {
        return dbCategoriaInventario.save(categoria);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbCategoriaInventario.deleteById(id);
        return "Éxito: Categoría eliminada: " + id;
    }
}
