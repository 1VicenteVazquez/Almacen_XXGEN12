package com.vicente.almacen.controllers;

import com.vicente.almacen.dto.ProblemaDoc;
import com.vicente.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.vicente.almacen.services.reportes.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Reportes económicos del negocio")
@ApiResponse(responseCode = "500", description = "Error interno del servidor",
        content = @Content(mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemaDoc.class)))
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/ventas-por-sucursal")
    @Operation(summary = "Reporte de ventas por sucursal",
            description = "Total facturado y piezas vendidas por sucursal. No incluye ventas canceladas.")
    @ApiResponse(responseCode = "200", description = "Reporte generado")
    public ResponseEntity<List<ReporteVentasSucursalResponse>> ventasPorSucursal() {
        return ResponseEntity.ok(reporteService.ventasPorSucursal());
    }
}