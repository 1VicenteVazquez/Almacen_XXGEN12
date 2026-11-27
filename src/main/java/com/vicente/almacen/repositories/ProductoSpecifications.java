package com.vicente.almacen.repositories;

import com.vicente.almacen.entities.Producto;
import com.vicente.almacen.enums.Categoria;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductoSpecifications {

    public static Specification<Producto> conFiltros(
            String nombre, Categoria categoria,
            BigDecimal precioMin, BigDecimal precioMax) {

        return nombreContiene(nombre)
                .and(categoriaIgual(categoria))
                .and(precioMinimo(precioMin))
                .and(precioMaximo(precioMax));
    }

    public static Specification<Producto> nombreContiene(String nombre) {
        return (root //representa la entidad principal sobre la que se hace la consutla
                , query //estrucutra completa de la consulta SELECTO DISTINCT ORDER, GROUP
                , cb) //constructor de condiciones =, >, <

                -> (nombre == null || nombre.isBlank())
                ? null
                : cb.like //clausula LIKE % actuan como comodines "cualquier texto antes y despues busqueda parcial
                (cb.lower //convierte todo a minusculas
                        (root.get("nombre")) //apunta al atributo
                , "%" + nombre.trim().toLowerCase() + "%");
    }

    public static Specification<Producto> categoriaIgual(Categoria categoria) {
        return (root, query, cb) -> categoria == null
                ? null
                : cb.equal(root.get("categoria"), categoria);
    }

    public static Specification<Producto> precioMinimo(BigDecimal min) {
        return (root, query, cb) -> min == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("precio"), min);
    }

    public static Specification<Producto> precioMaximo(BigDecimal max) {
        return (root, query, cb) -> max == null
                ? null
                : cb.lessThanOrEqualTo(root.get("precio"), max);
    }
}