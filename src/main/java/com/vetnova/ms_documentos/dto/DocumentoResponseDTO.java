package com.vetnova.ms_documentos.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoResponseDTO {

    private Long id;
    private Long fichaId;
    private Long mascotaId;
    private String tipoDocumento;
    private String descripcion;
    private String veterinario;
    private LocalDate fechaEmision;
}