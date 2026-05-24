package com.vetnova.ms_documentos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vetnova.ms_documentos.dto.FichaDTO;
import com.vetnova.ms_documentos.model.Documento;
import com.vetnova.ms_documentos.repository.DocumentoRepository;

@Service
public class DocumentoService {

    private final DocumentoRepository repository;
    private final WebClient webClient;

    public DocumentoService(DocumentoRepository repository, WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    public List<Documento> listar() {
        return repository.findAll();
    }

    public Documento guardar(Documento documento) {
        return repository.save(documento);
    }

    public FichaDTO obtenerFicha(Long id) {
        return webClient
                .get()
                .uri("http://localhost:8084/fichas/" + id)
                .retrieve()
                .bodyToMono(FichaDTO.class)
                .block();
    }
}