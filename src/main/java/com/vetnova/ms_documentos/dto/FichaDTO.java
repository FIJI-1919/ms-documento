package com.vetnova.ms_documentos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class FichaDTO {

    private Long id;
    private String mascota;
    private String diagnostico;
    private String tratamiento;
    private String veterinario;
}
