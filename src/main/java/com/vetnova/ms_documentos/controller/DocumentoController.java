package com.vetnova.ms_documentos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.vetnova.ms_documentos.model.Documento;
import com.vetnova.ms_documentos.service.DocumentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/documentos")

public class DocumentoController {

    private final DocumentoService service;

    public DocumentoController(DocumentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Documento> listar() {
        return service.listar();
    }

    @PostMapping
    public Documento guardar(@Valid @RequestBody Documento documento) {
        return service.guardar(documento);
    }
}