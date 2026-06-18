package com.vetnova.ms_documentos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vetnova.ms_documentos.dto.DocumentoRequestDTO;
import com.vetnova.ms_documentos.dto.DocumentoResponseDTO;
import com.vetnova.ms_documentos.dto.FichaDTO;
import com.vetnova.ms_documentos.service.DocumentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/documentos")
public class DocumentoController {

    private final DocumentoService service;

    public DocumentoController(DocumentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DocumentoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<DocumentoResponseDTO> guardar(
            @Valid @RequestBody DocumentoRequestDTO dto) {

        return new ResponseEntity<>(
                service.guardar(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DocumentoRequestDTO dto) {

        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);

        return ResponseEntity.ok("Documento eliminado correctamente");
    }

    @GetMapping("/fichas/{id}")
    public ResponseEntity<FichaDTO> obtenerFicha(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.obtenerFicha(id));
    }
}