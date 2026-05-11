package com.vetnova.ms_documentos.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La mascota es obligatoria")
    private String mascota;

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Size(min = 3, max = 50)
    private String tipoDocumento;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 5, max = 200)
    private String descripcion;

    @NotBlank(message = "El veterinario es obligatorio")
    private String veterinario;
}