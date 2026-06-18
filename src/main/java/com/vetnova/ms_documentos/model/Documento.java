package com.vetnova.ms_documentos.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fichaId;

    private Long mascotaId;

    private String tipoDocumento;

    private String descripcion;

    private String veterinario;

    private LocalDate fechaEmision;

    @PrePersist
    public void asignarFechaEmision() {
        this.fechaEmision = LocalDate.now();
    }
}