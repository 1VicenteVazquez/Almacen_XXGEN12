package com.vicente.almacen.mappers;

import com.vicente.almacen.dto.ventas.DetalleVentasResponse;
import com.vicente.almacen.dto.ventas.VentaResponse;
import com.vicente.almacen.entities.DetalleVenta;
import com.vicente.almacen.entities.Venta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VentaMapper {

    private final SucursalMapper sucursalMapper;

    public VentaResponse entidadAResponse(Venta venta) {
        return venta == null
                ? null
                : new VentaResponse(
                venta.getId(),
                venta.getFecha().toString(),
                venta.getEstadoVenta().getDescripcion(),
                sucursalMapper.entidadAResponse(venta.getSucursal()),
                venta.getDetalleVentas().stream()
                        .map(this::detalleAResponse)
                        .toList(),
                venta.total());
    }

    private DetalleVentasResponse detalleAResponse(DetalleVenta detalle) {
        return new DetalleVentasResponse(
                detalle.getProducto().getId(),
                detalle.getProducto().getNombre(),
                detalle.getCantidadProducto(),
                detalle.getPrecioProducto(),
                detalle.subtotal());
    }
}