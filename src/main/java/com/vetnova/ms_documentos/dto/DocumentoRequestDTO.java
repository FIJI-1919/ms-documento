package com.vetnova.ms_documentos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class DocumentoRequestDTO {

    @NotNull(message = "El ID de la ficha clínica es obligatorio")
    private Long fichaId;

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Size(min = 3, max = 50, message = "El tipo de documento debe tener entre 3 y 50 caracteres")
    private String tipoDocumento;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, max = 200, message = "La descripción debe tener entre 5 y 200 caracteres")
    private String descripcion;

    @NotBlank(message = "El veterinario es obligatorio")
    @Size(min = 2, max = 50, message = "El veterinario debe tener entre 2 y 50 caracteres")
    private String veterinario;
}