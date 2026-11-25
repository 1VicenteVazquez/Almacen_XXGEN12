package com.vicente.almacen.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Respuesta de error (RFC 9457 Problem Details)")
public record ProblemaDoc(

        @Schema(example = "about:blank")
        String type,

        @Schema(example = "Not Found")
        String title,

        @Schema(example = "404")
        int status,

        @Schema(example = "Producto no encontrado con id: 99")
        String detail,

        @Schema(example = "/api/productos/99")
        String instance,

        @Schema(description = "Lista de errores de validación (solo en 400)")
        List<String> errores
) {
}