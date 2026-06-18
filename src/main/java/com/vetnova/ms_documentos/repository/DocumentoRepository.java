package com.vetnova.ms_documentos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vetnova.ms_documentos.model.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    Optional<Documento> findByFichaIdAndTipoDocumentoIgnoreCase(
            Long fichaId,
            String tipoDocumento);
}