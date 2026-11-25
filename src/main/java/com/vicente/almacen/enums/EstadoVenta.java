package com.vicente.almacen.enums;

import com.vicente.almacen.utils.StringCustomUtils;
import com.vicente.almacen.utils.ValoresNumericosUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {
    REGISTRADA(1L, "Registrada"),
    CANCELADA(0L, "Cancelada");

    private final long codigo;
    private final String descripcion;

    public static EstadoVenta obtenerEstadoVentaPorDescripcion(String descripcion) {
        StringCustomUtils.validarNoVacio(descripcion, "La descripcion es requerida");

        String descripcionNormalizada = StringCustomUtils.normalizarTexto(descripcion);

        for (EstadoVenta estadoVenta : values()) {
            if (StringCustomUtils.normalizarTexto(estadoVenta.descripcion).equalsIgnoreCase(descripcionNormalizada)) {
                return estadoVenta;
            }
        }
        throw new IllegalArgumentException("No existe un estado de venta con la descripcion: " + descripcion);
    }

    public static EstadoVenta obtenerEstadoVentaPorCodigo(long codigo) {
        ValoresNumericosUtils.validarNumeroRequerido(codigo, "El codigo es requerido");

        for (EstadoVenta estadoVenta : values()) {
            if (estadoVenta.codigo == codigo) {
                return estadoVenta;
            }
        }
        throw new IllegalArgumentException("No existe un estado de venta con este codigo: " + codigo);
    }
}