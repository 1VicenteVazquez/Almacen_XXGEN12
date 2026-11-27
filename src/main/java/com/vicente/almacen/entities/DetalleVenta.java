package com.vicente.almacen.entities;

import com.vicente.almacen.exceptions.DatoInvalidoException;
import com.vicente.almacen.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;

import java.math.BigDecimal;
import java.net.PortUnreachableException;

@Entity
@Table(name = "DETALLE_VENTA")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_VENTA")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VENTA", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUCTO", nullable = false)
    private Producto producto;

    @Column(name = "CANTIDAD_PRODUCTO", nullable = false)
    private Integer cantidadProducto;

    @Column(name = "PRECIO_PRODUCTO", nullable = false)
    private BigDecimal precioProducto;

    public void asignarVenta(Venta venta) {
        if (venta == null) {
            throw new DatoInvalidoException("La venta es requerida");
        }
        this.venta = venta;
    }

    public static DetalleVenta crear(Producto producto, Integer cantidad){
        if (producto==null){
            throw new DatoInvalidoException("El producto es requerido");
        }
        ValoresNumericosUtils.validarEnteroPositivo(cantidad, "Cantidad debe ser positiva");

        return DetalleVenta.builder()
                .producto(producto)
                .cantidadProducto(cantidad)
                .precioProducto(producto.getPrecio()) //fotagrafia el producto
                .build();
    }

    public BigDecimal subtotal(){
        return precioProducto.multiply(BigDecimal.valueOf(cantidadProducto));
    }

    public BigDecimal getS() {
        return null;
    }
}