package com.vicente.almacen.dto.reportes;


import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;


@Schema(description = "Rendimiento económico de una sucursal")
public record ReporteVentasSucursalResponse(

        @Schema(description = "ID de la sucursal", example = "1")
        Long idSucursal,

        @Schema(description = "Nombre de la sucursal", example = "Sucursal Norte")
        String nombreSucursal,

        @Schema(description = "Total facturado en ventas activas", example = "30040.00")
        BigDecimal totalFacturado,

        @Schema(description = "Piezas vendidas en ventas activas", example = "3")
        Long cantidadProductosVendidos
) {
    // Si la sucursal no tiene ventas activas, SUM devuelve NULL: convertimos a 0
    public ReporteVentasSucursalResponse {
        totalFacturado = totalFacturado == null ? BigDecimal.ZERO : totalFacturado;
        cantidadProductosVendidos = cantidadProductosVendidos == null ? 0L : cantidadProductosVendidos;
    }
}