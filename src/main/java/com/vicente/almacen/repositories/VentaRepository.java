package com.vicente.almacen.repositories;

import com.vicente.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.vicente.almacen.entities.Venta;
import com.vicente.almacen.enums.EstadoVenta;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    // SELECT ... FROM VENTAS WHERE ESTADO = ?   (con sucursal, detalles y productos)
    @EntityGraph(attributePaths = {"sucursal", "detalleVentas", "detalleVentas.producto"}) //además de la venta, tráeme también su sucursal, sus detalles de venta y el producto de cada detalle
    List<Venta> findByEstadoVenta(EstadoVenta estadoVenta); //búscame todas las ventas cuyo estado coincida con el que te paso

    // SELECT ... FROM VENTAS WHERE ID_VENTA = ? AND ESTADO = ?
    @EntityGraph(attributePaths = {"sucursal", "detalleVentas", "detalleVentas.producto"})
    Optional<Venta> findByIdAndEstadoVenta(long id, EstadoVenta estadoVenta);

    //Query derivada ------No puede hacer SUM, GROUP BY ni cruzar 3 tablas con un LEFT JOIN condicionado.
    //Java Streams -----traer todas las ventas, detalles y productos a memoria, y sumar con groupingBy


    // @Query (JPQL) Oracle hace el trabajo y devuelve una fila por sucursal.
    @Query("""
        SELECT new com.vicente.almacen.dto.reportes.ReporteVentasSucursalResponse(
            s.id,
            s.nombre,
            SUM(d.cantidadProducto * d.precioProducto),
            SUM(d.cantidadProducto))
        FROM Sucursal s
        LEFT JOIN Venta v ON v.sucursal = s AND v.estadoVenta = :estado
        LEFT JOIN DetalleVenta d ON d.venta = v
        GROUP BY s.id, s.nombre
        ORDER BY s.id
        """)
    List<ReporteVentasSucursalResponse> reportePorSucursal(@Param("estado") EstadoVenta estado);

}
