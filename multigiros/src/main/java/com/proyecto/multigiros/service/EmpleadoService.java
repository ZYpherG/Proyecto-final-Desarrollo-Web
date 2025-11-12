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

import com.proyecto.multigiros.entity.Empleado;
import com.proyecto.multigiros.repository.EmpleadoRepository;

@RestController
@RequestMapping("/empleado")
@CrossOrigin
public class EmpleadoService {
     @Autowired
    private EmpleadoRepository dbEmpleado;

    @GetMapping("/listar")
    public List<Empleado> listar() {
        return dbEmpleado.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Empleado> buscar(@PathVariable Long id) {
        return dbEmpleado.findById(id);
    }

    @GetMapping("/buscarporcodigo/{codigo}")
    public Optional<Empleado> buscarporcodigo(@PathVariable String codigo) {
        return dbEmpleado.findByCodigoEmpleado(codigo);
    }

    @PostMapping("/insertar")
    public Empleado insertar(@RequestBody Empleado empleado) {
        return dbEmpleado.save(empleado);
    }

    @PutMapping("/modificar")
    public Empleado modificar(@RequestBody Empleado empleado) {
        return dbEmpleado.save(empleado);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbEmpleado.deleteById(id);
        return "Éxito: Empleado eliminado: " + id;
    }

    
}
