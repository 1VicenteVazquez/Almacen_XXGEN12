package com.vicente.almacen.controllers;

import com.vicente.almacen.dto.ProblemaDoc;
import com.vicente.almacen.dto.productos.ProductoRequest;
import com.vicente.almacen.dto.productos.ProductoResponse;
import com.vicente.almacen.services.productos.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController //convierta la clase en un controlador web
@RequestMapping("/api/productos")  //define la url base
@RequiredArgsConstructor //cosntructor para atributos final asi se hace la iyeccion de dependencias
@Tag //-agrupa todos los endpoints (swagger)
        (name = "Productos", description = "Gestión del inventario de productos")
// Errores que pueden ocurrir en cualquier endpoint:
@ApiResponse(responseCode = "400", description = "Datos o parámetros inválidos",
        content = @Content(mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemaDoc.class)))
@ApiResponse(responseCode = "500", description = "Error interno del servidor",
        content = @Content(mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemaDoc.class)))
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping //consultar leer datos
    @Operation(summary = "Listar prouctos", description = "Todos los filtros son opcionales")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")
    public ResponseEntity<List<ProductoResponse>> listar(
            @Parameter(description = "Búsqueda por nombre", example = "Laptop")
            @RequestParam  //captura parametros opcionales  ?
                    (required = false) String nombre,

            @Parameter(description = "Búsqueda por categoría", example = "Electrónica")
            @RequestParam(required = false) String categoria,

            @Parameter(description = "Búqueda por precio mínimo", example = "1000")
            @RequestParam(required = false) BigDecimal precioMin,

            @Parameter(description = "Búsqueda por precio máximo", example = "2000")
            @RequestParam(required = false) BigDecimal precioMax
    ){
        return  ResponseEntity.ok //devuelve el cofigo junto con los datos
                (productoService.listar(nombre, categoria, precioMin, precioMax));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrdo")
    @ApiResponse(responseCode = "404", description = "El producto no existe",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<ProductoResponse> obtenerPorId(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable   //captura el numero dentro de las llaves de la url
            @Positive(message = "El ID debe ser positivo") Long id

    ){
        return  ResponseEntity.ok(productoService.obtenerPorId(id));
    }


    @PostMapping //crear registrar productos nuevos
    @Operation(summary = "Registrar un nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto registrado")
    public ResponseEntity<ProductoResponse> registrar(
            @Valid //-activa el filtro de seguridad revisa las reglas antes descritas
            @RequestBody  //toma el cuerpo json compleeto del json y lo mete dentro de wel recod
            ProductoRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.registrar(request));
    }

    @PutMapping("/{id}") //actualiar recurso existente
    @Operation(summary = "Actualizar un producto existente")
    @ApiResponse(responseCode = "201", description = "Producto actualizado")
    @ApiResponse(responseCode = "409", description = "Conflicto con datos existentes",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<ProductoResponse> actualizar(

            @Parameter(description = "ID del producto", example = "1")
            @PathVariable @Positive(message = "El ID debe ser postivo") Long id,

            @Valid  @RequestBody ProductoRequest request
    ){
        return ResponseEntity.ok(productoService.actualizar(request, id));
    }


    @DeleteMapping("/{id}") // borrar recurso
    @Operation(summary = "Eliminar un producto")
    @ApiResponse(responseCode = "201", description = "Producto eliminado")
    @ApiResponse(responseCode = "409", description = "El producto está en uso y no puede eliminarse",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<ProductoResponse> eliminar(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable @Positive(message = "El ID deber ser positivo") Long id
    ){
        productoService.eliminar(id);
        return ResponseEntity.noContent() //estandar profesional al crear algo nuevo
                .build();
    }

}