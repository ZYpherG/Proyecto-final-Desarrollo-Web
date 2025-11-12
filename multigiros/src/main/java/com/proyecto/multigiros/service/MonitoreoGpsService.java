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

import com.proyecto.multigiros.entity.MonitoreoGps;
import com.proyecto.multigiros.repository.MonitoreoGpsRepository;

@RestController
@RequestMapping("/monitoreogps")
@CrossOrigin
public class MonitoreoGpsService {
    @Autowired
    private MonitoreoGpsRepository dbMonitoreoGps;

    @GetMapping("/listar")
    public List<MonitoreoGps> listar() {
        return dbMonitoreoGps.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<MonitoreoGps> buscar(@PathVariable Long id) {
        return dbMonitoreoGps.findById(id);
    }

    @GetMapping("/buscarporDispositivo/{idDispositivo}")
    public List<MonitoreoGps> buscarporDispositivo(@PathVariable Long idDispositivo) {
        return dbMonitoreoGps.findByDispositivoIdDispositivo(idDispositivo);
    }

    @PostMapping("/insertar")
    public MonitoreoGps insertar(@RequestBody MonitoreoGps monitoreo) {
        return dbMonitoreoGps.save(monitoreo);
    }

    @PutMapping("/modificar")
    public MonitoreoGps modificar(@RequestBody MonitoreoGps monitoreo) {
        return dbMonitoreoGps.save(monitoreo);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbMonitoreoGps.deleteById(id);
        return "Éxito: Monitoreo GPS eliminado: " + id;
    }
}
