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

import com.proyecto.multigiros.entity.DispositivoGps;
import com.proyecto.multigiros.repository.DispositivoGpsRepository;

@RestController
@RequestMapping("/dispositivogps")
@CrossOrigin
public class DispositivoGpsService {
   @Autowired
    private DispositivoGpsRepository dbDispositivoGps;

    @GetMapping("/listar")
    public List<DispositivoGps> listar() {
        return dbDispositivoGps.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<DispositivoGps> buscar(@PathVariable Long id) {
        return dbDispositivoGps.findById(id);
    }

    @GetMapping("/buscarporcodigo/{codigo}")
    public Optional<DispositivoGps> buscarporcodigo(@PathVariable String codigo) {
        return dbDispositivoGps.findByCodigoDispositivo(codigo);
    }

    @GetMapping("/buscarporplaca/{placa}")
    public Optional<DispositivoGps> buscarporplaca(@PathVariable String placa) {
        return dbDispositivoGps.findByPlacaVehiculo(placa);
    }

    @PostMapping("/insertar")
    public DispositivoGps insertar(@RequestBody DispositivoGps dispositivo) {
        return dbDispositivoGps.save(dispositivo);
    }

    @PutMapping("/modificar")
    public DispositivoGps modificar(@RequestBody DispositivoGps dispositivo) {
        return dbDispositivoGps.save(dispositivo);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbDispositivoGps.deleteById(id);
        return "Éxito: Dispositivo GPS eliminado: " + id;
    }
 
}
