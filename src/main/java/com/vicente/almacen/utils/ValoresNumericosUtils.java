package com.vicente.almacen.utils;

import com.vicente.almacen.exceptions.DatoInvalidoExceptions;

import java.math.BigDecimal;

public class ValoresNumericosUtils {

    public static <N extends Number> void validarNumeroRequerido(N numero, String mensaje) {
        if (numero == null) {
            throw new DatoInvalidoExceptions(mensaje);
        }
    }

    public static void validarEnteroPositivo(Integer numero, String mensaje) {

        validarNumeroRequerido(numero, mensaje);

        if (numero <= 0) {
            throw new DatoInvalidoExceptions(mensaje);
        }
    }

    public static void validarBigDecimalPositivo(BigDecimal numero, String mensaje) {

        validarNumeroRequerido(numero, mensaje);

        if (numero.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DatoInvalidoExceptions(mensaje);
        }
    }
}