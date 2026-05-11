package com.vetnova.ms_documentos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vetnova.ms_documentos.model.Documento;
import com.vetnova.ms_documentos.repository.DocumentoRepository;

@Service
public class DocumentoService {

    private final DocumentoRepository repository;

    public DocumentoService(DocumentoRepository repository) {
        this.repository = repository;
    }

    public List<Documento> listar() {
        return repository.findAll();
    }

    public Documento guardar(Documento documento) {
        return repository.save(documento);
    }
}