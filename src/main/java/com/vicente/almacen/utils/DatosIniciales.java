package com.vicente.almacen.utils;

import com.vicente.almacen.entities.Producto;
import com.vicente.almacen.entities.Sucursal;
import com.vicente.almacen.enums.Categoria;
import com.vicente.almacen.repositories.ProductoRepository;
import com.vicente.almacen.repositories.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DatosIniciales implements CommandLineRunner {

    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception {

        if (productoRepository.count() == 0) {

            productoRepository.saveAll(List.of(
                    new Producto(
                            null,
                            "Laptop Gamer",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(15000),
                            10),

                    new Producto(
                            null,
                            "Mouse Inalámbrico",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(20),
                            10),

                    new Producto(
                            null,
                            "Camiseta deportiva",
                            Categoria.ROPA,
                            BigDecimal.valueOf(20),
                            10)
            ));

            log.info("Productos de prueba cargados correctamente");
        }

        if (sucursalRepository.count() == 0) {

            sucursalRepository.saveAll(List.of(
                    new Sucursal(
                            null,
                            "Sucursal Norte",
                            "Av. Norte"),

                    new Sucursal(
                            null,
                            "Sucursal Centro",
                            "Av. Centro"),

                    new Sucursal(
                            null,
                            "Sucursal Sur",
                            "Av. Sur")
            ));

            log.info("Sucursales de prueba cargadas correctamente");
        }
    }
}