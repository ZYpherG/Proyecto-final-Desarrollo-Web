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

import com.proyecto.multigiros.entity.Cliente;
import com.proyecto.multigiros.repository.ClienteRepository;

@RestController
@RequestMapping("/cliente")
@CrossOrigin
public class ClienteService {
    @Autowired
    private ClienteRepository dbCliente;

    @GetMapping("/listar")
    public List<Cliente> listar() {
        return dbCliente.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Cliente> buscar(@PathVariable Long id) {
        return dbCliente.findById(id);
    }

    @GetMapping("/buscarporpersona/{idPersona}")
    public Optional<Cliente> buscarporpersona(@PathVariable Long idPersona) {
        return dbCliente.findByPersonaIdPersona(idPersona);
    }

    @PostMapping("/insertar")
    public Cliente insertar(@RequestBody Cliente cliente) {
        return dbCliente.save(cliente);
    }

    @PutMapping("/modificar")
    public Cliente modificar(@RequestBody Cliente cliente) {
        return dbCliente.save(cliente);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbCliente.deleteById(id);
        return "Éxito: Cliente eliminado: " + id;
    }
}
