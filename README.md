# Microservicio Documentos - VetNova

Microservicio encargado de administrar los documentos clínicos asociados a las fichas médicas del sistema VetNova.

## Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* Spring WebClient
* MySQL
* Maven
* Lombok
* JUnit 5
* Mockito

## Funcionalidades

* Listar documentos.
* Buscar documento por ID.
* Registrar documentos.
* Actualizar documentos.
* Eliminar documentos.
* Consultar información de una ficha clínica mediante comunicación con el microservicio ms-ficha.

## Puerto utilizado

8086

## Base de datos

documentos_db

## Endpoints principales

### Listar documentos

GET /api/v1/documentos

### Buscar documento por ID

GET /api/v1/documentos/{id}

### Registrar documento

POST /api/v1/documentos

### Actualizar documento

PUT /api/v1/documentos/{id}

### Eliminar documento

DELETE /api/v1/documentos/{id}

### Obtener ficha clínica

GET /api/v1/documentos/fichas/{id}

## Validaciones implementadas

* ID de ficha obligatorio.
* Tipo de documento obligatorio.
* Solo se permiten los tipos:

  * RECETA
  * ORDEN_EXAMEN
  * CERTIFICADO
* Descripción obligatoria.
* Veterinario obligatorio.
* No se permite registrar dos documentos del mismo tipo para una misma ficha clínica.
* Validación de existencia de la ficha clínica mediante WebClient.

## Manejo de excepciones

* Documento no encontrado.
* Ficha clínica no encontrada.
* Error de comunicación con ms-ficha.
* Reglas de negocio.
* Errores de validación.
* Error interno del servidor.

## Logs implementados

El microservicio registra:

* Listado de documentos.
* Búsquedas por ID.
* Registro de documentos.
* Actualización de documentos.
* Eliminación de documentos.
* Validación de fichas clínicas.
* Errores del sistema.

## Pruebas unitarias

Se implementaron pruebas unitarias para:

* Controlador (Controller)
* Servicio (Service)

Resultado:

* 10 pruebas ejecutadas.
* 0 fallos.

## Ejecución del proyecto

```bash
mvn spring-boot:run
```

## Autor

Adriano Contreras
