package com.vetnova.ms_documentos.controller;

import tools.jackson.databind.ObjectMapper;

import com.vetnova.ms_documentos.dto.DocumentoRequestDTO;
import com.vetnova.ms_documentos.dto.DocumentoResponseDTO;
import com.vetnova.ms_documentos.service.DocumentoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DocumentoControllerTest {

    private MockMvc mockMvc;

    private DocumentoService service;

    private ObjectMapper mapper;

    private DocumentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {

        service = mock(DocumentoService.class);

        DocumentoController controller =
                new DocumentoController(service);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        mapper = new ObjectMapper();

        responseDTO = new DocumentoResponseDTO(
                1L,
                10L,
                20L,
                "RECETA",
                "Medicamento",
                "Dr. Pérez",
                LocalDate.now()
        );
    }

    @Test
    void debeListarDocumentos() throws Exception {

        when(service.listar())
                .thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/v1/documentos"))
                .andExpect(status().isOk());
    }

    @Test
    void debeBuscarDocumentoPorId() throws Exception {

        when(service.buscarPorId(1L))
                .thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/documentos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void debeGuardarDocumento() throws Exception {

        DocumentoRequestDTO request =
                new DocumentoRequestDTO();

        request.setFichaId(10L);
        request.setTipoDocumento("RECETA");
        request.setDescripcion("Medicamento");
        request.setVeterinario("Dr. Pérez");

        when(service.guardar(any()))
                .thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/documentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void debeActualizarDocumento() throws Exception {

        DocumentoRequestDTO request =
                new DocumentoRequestDTO();

        request.setFichaId(10L);
        request.setTipoDocumento("RECETA");
        request.setDescripcion("Actualizado");
        request.setVeterinario("Dr. Pérez");

        when(service.actualizar(eq(1L), any()))
                .thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/documentos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
void debeEliminarDocumento() throws Exception {

    doNothing().when(service).eliminar(1L);

    mockMvc.perform(delete("/api/v1/documentos/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Documento eliminado correctamente"));
}
}