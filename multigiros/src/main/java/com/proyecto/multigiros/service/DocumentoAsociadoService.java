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

import com.proyecto.multigiros.entity.DocumentoAsociado;
import com.proyecto.multigiros.repository.DocumentoAsociadoRepository;

@RestController
@RequestMapping("/documentoasociado")
@CrossOrigin
public class DocumentoAsociadoService {
    @Autowired
    private DocumentoAsociadoRepository dbDocumentoAsociado;

    @GetMapping("/listar")
    public List<DocumentoAsociado> listar() {
        return dbDocumentoAsociado.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<DocumentoAsociado> buscar(@PathVariable Long id) {
        return dbDocumentoAsociado.findById(id);
    }

    @GetMapping("/buscarpororden/{idOrden}")
    public List<DocumentoAsociado> buscarpororden(@PathVariable Long idOrden) {
        return dbDocumentoAsociado.findByOrdenIdOrden(idOrden);
    }

    @PostMapping("/insertar")
    public DocumentoAsociado insertar(@RequestBody DocumentoAsociado documento) {
        return dbDocumentoAsociado.save(documento);
    }

    @PutMapping("/modificar")
    public DocumentoAsociado modificar(@RequestBody DocumentoAsociado documento) {
        return dbDocumentoAsociado.save(documento);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbDocumentoAsociado.deleteById(id);
        return "Éxito: Documento asociado eliminado: " + id;
    }
}
