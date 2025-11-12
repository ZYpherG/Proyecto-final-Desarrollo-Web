package com.proyecto.multigiros.repository;

import com.proyecto.multigiros.entity.DocumentoAsociado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("DocumentoAsociadoRepository")
public interface DocumentoAsociadoRepository extends JpaRepository<DocumentoAsociado, Long> {
    List<DocumentoAsociado> findByOrdenIdOrden(Long idOrden);
    List<DocumentoAsociado> findByTipoDocumento(DocumentoAsociado.TipoDocumento tipoDocumento);
    List<DocumentoAsociado> findByUsuarioCarga(String usuarioCarga);
}