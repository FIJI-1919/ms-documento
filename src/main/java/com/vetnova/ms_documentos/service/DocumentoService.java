package com.vetnova.ms_documentos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.vetnova.ms_documentos.dto.DocumentoRequestDTO;
import com.vetnova.ms_documentos.dto.DocumentoResponseDTO;
import com.vetnova.ms_documentos.dto.FichaDTO;
import com.vetnova.ms_documentos.exception.DocumentoNoEncontradoException;
import com.vetnova.ms_documentos.exception.ErrorComunicacionException;
import com.vetnova.ms_documentos.exception.FichaNoEncontradaException;
import com.vetnova.ms_documentos.exception.ReglaNegocioException;
import com.vetnova.ms_documentos.model.Documento;
import com.vetnova.ms_documentos.repository.DocumentoRepository;

@Service
public class DocumentoService {

    private static final Logger logger =
            LoggerFactory.getLogger(DocumentoService.class);

    private final DocumentoRepository repository;
    private final WebClient webClient;

    public DocumentoService(
            DocumentoRepository repository,
            WebClient webClient) {

        this.repository = repository;
        this.webClient = webClient;
    }

    public List<DocumentoResponseDTO> listar() {
        logger.info("Listando documentos");

        return repository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public DocumentoResponseDTO buscarPorId(Long id) {
        logger.info("Buscando documento con ID: " + id);

        Documento documento = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Documento no encontrado con ID: " + id);
                    return new DocumentoNoEncontradoException(
                            "Documento no encontrado");
                });

        return convertirAResponse(documento);
    }

    public DocumentoResponseDTO guardar(DocumentoRequestDTO dto) {
        logger.info("Registrando documento para ficha ID: " + dto.getFichaId());

        FichaDTO ficha = obtenerFichaPorId(dto.getFichaId());

        String tipoNormalizado = normalizarTipoDocumento(dto.getTipoDocumento());

        validarDocumentoDuplicado(dto.getFichaId(), tipoNormalizado);

        Documento documento = new Documento();

        documento.setFichaId(ficha.getId());
        documento.setMascotaId(ficha.getMascotaId());
        documento.setTipoDocumento(tipoNormalizado);
        documento.setDescripcion(dto.getDescripcion());
        documento.setVeterinario(dto.getVeterinario());

        Documento documentoGuardado = repository.save(documento);

        logger.info("Documento registrado con ID: " + documentoGuardado.getId());

        return convertirAResponse(documentoGuardado);
    }

    public DocumentoResponseDTO actualizar(Long id, DocumentoRequestDTO dto) {
        logger.info("Actualizando documento con ID: " + id);

        Documento documento = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Documento no encontrado con ID: " + id);
                    return new DocumentoNoEncontradoException(
                            "Documento no encontrado");
                });

        FichaDTO ficha = obtenerFichaPorId(dto.getFichaId());

        String tipoNormalizado = normalizarTipoDocumento(dto.getTipoDocumento());

        validarCambioDocumentoEnActualizacion(
                id,
                dto.getFichaId(),
                tipoNormalizado);

        documento.setFichaId(ficha.getId());
        documento.setMascotaId(ficha.getMascotaId());
        documento.setTipoDocumento(tipoNormalizado);
        documento.setDescripcion(dto.getDescripcion());
        documento.setVeterinario(dto.getVeterinario());

        Documento documentoActualizado = repository.save(documento);

        logger.info("Documento actualizado con ID: " + documentoActualizado.getId());

        return convertirAResponse(documentoActualizado);
    }

    public void eliminar(Long id) {
        logger.info("Eliminando documento con ID: " + id);

        Documento documento = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Documento no encontrado con ID: " + id);
                    return new DocumentoNoEncontradoException(
                            "Documento no encontrado");
                });

        repository.delete(documento);

        logger.info("Documento eliminado con ID: " + id);
    }

    public FichaDTO obtenerFicha(Long id) {
        return obtenerFichaPorId(id);
    }

    private FichaDTO obtenerFichaPorId(Long fichaId) {
        try {
            logger.info("Validando ficha clínica con ID: " + fichaId);

            return webClient
                    .get()
                    .uri("http://localhost:8085/api/v1/fichas/" + fichaId)
                    .retrieve()
                    .bodyToMono(FichaDTO.class)
                    .block();

        } catch (WebClientResponseException.NotFound e) {
            logger.error("Ficha clínica no encontrada con ID: " + fichaId);

            throw new FichaNoEncontradaException(
                    "La ficha clínica con ID " + fichaId + " no existe");

        } catch (Exception e) {
            logger.error("Error al comunicarse con ms-ficha");

            throw new ErrorComunicacionException(
                    "No se pudo validar la ficha clínica. Verifica que ms-ficha esté funcionando");
        }
    }

    private String normalizarTipoDocumento(String tipoDocumento) {
        String tipo = tipoDocumento.toUpperCase();

        if (!tipo.equals("RECETA")
                && !tipo.equals("ORDEN_EXAMEN")
                && !tipo.equals("CERTIFICADO")) {

            throw new ReglaNegocioException(
                    "El tipo de documento debe ser RECETA, ORDEN_EXAMEN o CERTIFICADO");
        }

        return tipo;
    }

    private void validarDocumentoDuplicado(
            Long fichaId,
            String tipoDocumento) {

        repository.findByFichaIdAndTipoDocumentoIgnoreCase(fichaId, tipoDocumento)
                .ifPresent(documento -> {
                    logger.error("Ya existe documento "
                            + tipoDocumento
                            + " para ficha ID: "
                            + fichaId);

                    throw new ReglaNegocioException(
                            "Ya existe un documento de este tipo para la ficha indicada");
                });
    }

    private void validarCambioDocumentoEnActualizacion(
            Long documentoId,
            Long fichaId,
            String tipoDocumento) {

        repository.findByFichaIdAndTipoDocumentoIgnoreCase(fichaId, tipoDocumento)
                .ifPresent(documentoExistente -> {
                    if (!documentoExistente.getId().equals(documentoId)) {
                        logger.error("La ficha ID "
                                + fichaId
                                + " ya tiene documento tipo "
                                + tipoDocumento);

                        throw new ReglaNegocioException(
                                "La ficha indicada ya tiene un documento de este tipo");
                    }
                });
    }

    private DocumentoResponseDTO convertirAResponse(Documento documento) {
        return new DocumentoResponseDTO(
                documento.getId(),
                documento.getFichaId(),
                documento.getMascotaId(),
                documento.getTipoDocumento(),
                documento.getDescripcion(),
                documento.getVeterinario(),
                documento.getFechaEmision()
        );
    }
}