package com.vicente.almacen.services.reportes;

import com.vicente.almacen.dto.reportes.ReporteVentasSucursalResponse;

import java.util.List;

public interface ReporteService {

    List<ReporteVentasSucursalResponse> ventasPorSucursal();
}