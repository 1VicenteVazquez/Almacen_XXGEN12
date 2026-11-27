package com.vicente.almacen.services.ventas;

import com.vicente.almacen.dto.ventas.DetalleVentaRequest;
import com.vicente.almacen.dto.ventas.VentaResponse;
import com.vicente.almacen.dto.ventas.VentasRequest;
import com.vicente.almacen.entities.DetalleVenta;
import com.vicente.almacen.entities.Producto;
import com.vicente.almacen.entities.Sucursal;
import com.vicente.almacen.entities.Venta;
import com.vicente.almacen.enums.EstadoVenta;
import com.vicente.almacen.exceptions.DatoInvalidoException;
import com.vicente.almacen.exceptions.RecursoNoEncontradoException;
import com.vicente.almacen.mappers.VentaMapper;
import com.vicente.almacen.repositories.ProductoRepository;
import com.vicente.almacen.repositories.SucursalRepository;
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
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    private final VentaMapper ventaMapper;


    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listar() {
        log.info("Listando ventas REGISTRADAS ......");

        return ventaRepository.findByEstadoVenta(EstadoVenta.REGISTRADA).stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listarCanceladas() {
        log.info("Listando ventas CANCELADAS ......");

        return ventaRepository.findByEstadoVenta(EstadoVenta.CANCELADA).stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public VentaResponse obtenerPorIdActiva(long id) {
        log.info("Buscando venta activa con id: {}", id);

        return ventaRepository.findByIdAndEstadoVenta(id, EstadoVenta.REGISTRADA)
                .map(ventaMapper::entidadAResponse)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Venta activa no encontrada con id: " + id));
    }


    @Override
    public VentaResponse registrar(VentasRequest request) {
        log.info("Registrando venta en sucursal con id: {}", request.idSucursal());

        // sucursal debe existir
        Sucursal sucursal = sucursalRepository.findById(request.idSucursal())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Sucursal no encontrada con id: " + request.idSucursal()));

        Venta venta = Venta.crear(sucursal);

        // x cada producto: buscar, validar stock, descontar, crear detalle
        for (DetalleVentaRequest item : request.productos()) {

            Producto producto = productoRepository.findById(item.idProducto())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "Producto no encontrado con id: " + item.idProducto()));

            if (item.cantidadProducto() > producto.getCantidad()) {
                log.error("Stock insuficiente para '{}': solicitado {}, disponible {}",

                        producto.getNombre(), item.cantidadProducto(), producto.getCantidad());
                throw new DatoInvalidoException(
                        "Stock insuficiente para '" + producto.getNombre()
                                + "'. Disponible: " + producto.getCantidad()
                                + ", solicitado: " + item.cantidadProducto());
            }

            producto.descontarCantidad(item.cantidadProducto());
            venta.agregarDetalle(DetalleVenta.crear(producto, item.cantidadProducto()));
        }

        // Guardar venta + detalles
        ventaRepository.saveAndFlush(venta);
        log.info("Venta {} registrada. Total: {}", venta.getId(), venta.total());

        return ventaMapper.entidadAResponse(venta);
    }


    @Override
    public void cancelar(long id) {
        log.info("Cancelando venta con id: {}", id);

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Venta no encontrada con id: " + id));

        // Cambia el estado. Si ya estaba cancelada lanza ConflictoException (409)
        venta.cancelar();

        // Devuelve el stock de cada producto vendido
        for (DetalleVenta detalle : venta.getDetalleVentas()) {
            detalle.getProducto().aumentarCantidad(detalle.getCantidadProducto());
        }

        ventaRepository.saveAndFlush(venta);
        log.info("Venta {} cancelada y stock devuelto", id);
    }
}
