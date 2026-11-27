package com.vicente.almacen.services.reportes;

import com.vicente.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.vicente.almacen.enums.EstadoVenta;
import com.vicente.almacen.repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReporteServiceImpl implements ReporteService {

    private final VentaRepository ventaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReporteVentasSucursalResponse> ventasPorSucursal() {
        log.info("Generando reporte de ventas por sucursal (solo ventas REGISTRADAS)");

        List<ReporteVentasSucursalResponse> reporte =
                ventaRepository.reportePorSucursal(EstadoVenta.REGISTRADA);

        log.info("Reporte generado para {} sucursales", reporte.size());
        return reporte;
    }
}