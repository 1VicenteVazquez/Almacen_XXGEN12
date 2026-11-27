package com.vicente.almacen.entities;

import com.vicente.almacen.enums.Categoria;
import com.vicente.almacen.exceptions.DatoInvalidoException;
import com.vicente.almacen.utils.StringCustomUtils;
import com.vicente.almacen.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity  //Le dine a java que esta clase no es solo codigo si no que representa una tabla
@Table(name = "PRODUCTOS")
@NoArgsConstructor  //crea en automatico un constructor vacio new Producto() para que JPA pueda leer ña tabla
@AllArgsConstructor   //crea un constructor con todos los campos includos
@Builder  //permite crea los objetos de forma limpia y legible  crear()
@Getter  //crea en automatico todos los metodos get, getNombre
public class Producto {

    @Id //marca el atributo como PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //equivale a un AUTO INCREMENT
    @Column(name = "ID_PRODUCTO") //COLUMNA
    private Long id;

    @Column(name = "NOMBRE", length = 30, nullable = false) // NULLABLE EQUIVALE A NOT NULL
    private String nombre;

    @Column(name = "CATEGORIA", nullable = false)
    @Enumerated(EnumType.STRING) //guarda el nombre contextual de una enumeracion de Java Een una columna de bd
    private Categoria categoria;

    @Column(name = "PRECIO", nullable = false, precision =
            10, scale = 2) //equivale a decimales (12345678.99)
    private BigDecimal precio;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    public static void validarDatos(
            String nombre, Categoria categoria,
            BigDecimal precio, Integer cantidad) {

        StringCustomUtils.validarTamanio(nombre, 5, 30,
                "El nombre es requerido y debe tener entre 5 y 30 caracteres");

        if (categoria == null) {
            throw new DatoInvalidoException("La categoria es requerida");
        }

        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new DatoInvalidoException(
                    "El precio es requerido y no puede ser negativo");
        }

        ValoresNumericosUtils.validarEnteroPositivo(cantidad,
                "La cantidad es requerida y debe ser positiva");
    }

    public void actualizar(
            String nombre, Categoria categoria,
            BigDecimal precio, Integer cantidad) {

        validarDatos(nombre, categoria, precio, cantidad);

        this.nombre = nombre.trim();
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public void aumentarCantidad(int cantidad) {
        ValoresNumericosUtils.validarEnteroPositivo(
                cantidad,
                "La cantidad debe ser positiva");

        this.cantidad += cantidad;
    }

    public void descontarCantidad(int cantidad) {
        ValoresNumericosUtils.validarEnteroPositivo(
                cantidad,
                "La cantidad debe ser positiva");

        if (cantidad > this.cantidad) {
            throw new DatoInvalidoException(
                    "La cantidad debe ser menor o igual a la cantidad actual");
        }

        this.cantidad -= cantidad;
    }

    public static Producto crear(
            String nombre, Categoria categoria,
            BigDecimal precio, Integer cantidad) {

        validarDatos(nombre, categoria, precio, cantidad);

        return Producto.builder()
                .nombre(nombre.trim())
                .categoria(categoria)
                .precio(precio)
                .cantidad(cantidad)
                .build();
    }
}