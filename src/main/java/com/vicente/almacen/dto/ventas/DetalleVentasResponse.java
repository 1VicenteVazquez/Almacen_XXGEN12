package com.vicente.almacen.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Detalle de un producto dentro de una venta")
public record DetalleVentasResponse(

        @Schema(description = "ID del producto", example = "1")
        long idProducto,

        @Schema(description = "Nombre del producto", example = "Laptop Gamer")
        String nombreProducto,

        @Schema(description = "Cantidad de unidades vendidas", example = "2")
        int cantidad,

        @Schema(description = "Precio unitario del producto", example = "15000.00")
        BigDecimal precioProducto,

        @Schema(description = "Subtotal del producto", example = "30000.00")
        BigDecimal subtotal
) {}