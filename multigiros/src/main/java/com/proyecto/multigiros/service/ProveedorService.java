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

import com.proyecto.multigiros.entity.Proveedor;
import com.proyecto.multigiros.repository.ProveedorRepository;

@RestController
@RequestMapping("/proveedor")
@CrossOrigin
public class ProveedorService {
    @Autowired
    private ProveedorRepository dbProveedor;

    @GetMapping("/listar")
    public List<Proveedor> listar() {
        return dbProveedor.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Proveedor> buscar(@PathVariable Long id) {
        return dbProveedor.findById(id);
    }

    @GetMapping("/buscarporpersona/{idPersona}")
    public Optional<Proveedor> buscarporpersona(@PathVariable Long idPersona) {
        return dbProveedor.findByPersonaIdPersona(idPersona);
    }

    @PostMapping("/insertar")
    public Proveedor insertar(@RequestBody Proveedor proveedor) {
        return dbProveedor.save(proveedor);
    }

    @PutMapping("/modificar")
    public Proveedor modificar(@RequestBody Proveedor proveedor) {
        return dbProveedor.save(proveedor);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbProveedor.deleteById(id);
        return "Éxito: Proveedor eliminado: " + id;
    }
}
