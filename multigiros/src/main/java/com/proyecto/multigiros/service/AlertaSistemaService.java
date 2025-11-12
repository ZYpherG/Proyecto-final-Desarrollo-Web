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

import com.proyecto.multigiros.entity.AlertaSistema;
import com.proyecto.multigiros.repository.AlertaSistemaRepository;

@RestController
@RequestMapping("/alertasistema")
@CrossOrigin
public class AlertaSistemaService {
     @Autowired
    private AlertaSistemaRepository dbAlertaSistema;

    @GetMapping("/listar")
    public List<AlertaSistema> listar() {
        return dbAlertaSistema.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<AlertaSistema> buscar(@PathVariable Long id) {
        return dbAlertaSistema.findById(id);
    }

    @GetMapping("/buscarporusuario/{idUsuario}")
    public List<AlertaSistema> buscarporusuario(@PathVariable Long idUsuario) {
        return dbAlertaSistema.findByUsuarioDestinoIdUsuario(idUsuario);
    }

    @PostMapping("/insertar")
    public AlertaSistema insertar(@RequestBody AlertaSistema alerta) {
        return dbAlertaSistema.save(alerta);
    }

    @PutMapping("/modificar")
    public AlertaSistema modificar(@RequestBody AlertaSistema alerta) {
        return dbAlertaSistema.save(alerta);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbAlertaSistema.deleteById(id);
        return "Éxito: Alerta sistema eliminada: " + id;
    }
}
