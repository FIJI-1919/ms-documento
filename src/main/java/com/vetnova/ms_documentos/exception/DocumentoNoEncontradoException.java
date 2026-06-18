package com.vetnova.ms_documentos.exception;

public class DocumentoNoEncontradoException extends RuntimeException {

    public DocumentoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}