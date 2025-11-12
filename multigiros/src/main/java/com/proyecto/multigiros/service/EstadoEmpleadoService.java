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

import com.proyecto.multigiros.entity.EstadoEmpleado;
import com.proyecto.multigiros.repository.EstadoEmpleadoRepository;

@RestController
@RequestMapping("/estadoempleado")
@CrossOrigin
public class EstadoEmpleadoService {
     @Autowired
    private EstadoEmpleadoRepository dbEstadoEmpleado;

    @GetMapping("/listar")
    public List<EstadoEmpleado> listar() {
        return dbEstadoEmpleado.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<EstadoEmpleado> buscar(@PathVariable Long id) {
        return dbEstadoEmpleado.findById(id);
    }

    @GetMapping("/buscarpornombre/{nombre}")
    public Optional<EstadoEmpleado> buscarpornombre(@PathVariable String nombre) {
        return dbEstadoEmpleado.findByEstado(nombre);
    }

    @PostMapping("/insertar")
    public EstadoEmpleado insertar(@RequestBody EstadoEmpleado estado) {
        return dbEstadoEmpleado.save(estado);
    }

    @PutMapping("/modificar")
    public EstadoEmpleado modificar(@RequestBody EstadoEmpleado estado) {
        return dbEstadoEmpleado.save(estado);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbEstadoEmpleado.deleteById(id);
        return "Éxito: Estado empleado eliminado: " + id;
    }

}
