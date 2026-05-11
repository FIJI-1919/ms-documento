package com.vetnova.ms_documentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vetnova.ms_documentos.model.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

}