package com.vetnova.ms_documentos.service;

import com.vetnova.ms_documentos.dto.DocumentoResponseDTO;
import com.vetnova.ms_documentos.exception.DocumentoNoEncontradoException;
import com.vetnova.ms_documentos.model.Documento;
import com.vetnova.ms_documentos.repository.DocumentoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentoServiceTest {

    @Mock
    private DocumentoRepository repository;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private DocumentoService service;

    private Documento documento;

    @BeforeEach
    void setUp() {

        documento = new Documento();

        documento.setId(1L);
        documento.setFichaId(10L);
        documento.setMascotaId(20L);
        documento.setTipoDocumento("RECETA");
        documento.setDescripcion("Medicamento");
        documento.setVeterinario("Dr. Pérez");
        documento.setFechaEmision(LocalDate.now());
    }

    @Test
    void debeListarDocumentos() {

        when(repository.findAll())
                .thenReturn(List.of(documento));

        List<DocumentoResponseDTO> resultado =
                service.listar();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void debeBuscarDocumentoPorId() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(documento));

        DocumentoResponseDTO resultado =
                service.buscarPorId(1L);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void debeLanzarExcepcionSiDocumentoNoExiste() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                DocumentoNoEncontradoException.class,
                () -> service.buscarPorId(99L)
        );
    }

    @Test
    void debeEliminarDocumento() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(documento));

        service.eliminar(1L);

        verify(repository).delete(documento);
    }

    @Test
    void debeLanzarExcepcionAlEliminarDocumentoInexistente() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                DocumentoNoEncontradoException.class,
                () -> service.eliminar(99L)
        );
    }
}