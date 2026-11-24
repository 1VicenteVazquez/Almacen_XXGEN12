package com.vicente.almacen.enums;

import com.vicente.almacen.exceptions.DatoInvalidoExceptions;
import com.vicente.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Categoria {

    ALIMENTO("Alimento"),
    HIGIENE("Higiene"),
    JUGUETE("Juguete"),
    ELECTRONICA("Electrónica"),
    ROPA("Ropa"),
    ACCESORIO("Accesorio"),
    FARMACIA("Farmacia");

    private final String descripcion;

    public static Categoria obtenerCategoriaPorDescripcion(String descripcion) {

        StringCustomUtils.validarNoVacio(descripcion, "La descripcion es requerida");

        String descripcionNormalizada = StringCustomUtils.normalizarTexto(descripcion);

        for (Categoria categoria : values()) {
            if (StringCustomUtils.normalizarTexto(categoria.descripcion)
                    .equalsIgnoreCase(descripcionNormalizada)) {
                return categoria;
            }
        }

        throw new DatoInvalidoExceptions(
                "No existe una categoria con la descripcion: " + descripcion);
    }
}