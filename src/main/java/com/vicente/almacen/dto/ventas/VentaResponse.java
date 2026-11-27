package com.vicente.almacen.dto.ventas;

import com.vicente.almacen.dto.sucursales.SucursalResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Datos de una venta")
public record VentaResponse(

        @Schema(description = "Identificador de la venta", example = "1")
        long id,

        @Schema(description = "Fecha de la venta", example = "2026-02-28")
        String fecha,

        @Schema(description = "Estado de la venta", example = "Registrada")
        String estado,

        @Schema(description = "Sucursal donde se realizó la venta")
        SucursalResponse sucursal,

        @Schema(description = "Lista de productos de la venta")
        List<DetalleVentasResponse> detalles,

        @Schema(description = "Total de la venta", example = "15000.00")
        BigDecimal total
) {}