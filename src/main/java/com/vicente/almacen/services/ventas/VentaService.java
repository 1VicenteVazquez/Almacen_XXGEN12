package com.vicente.almacen.services.ventas;


import com.vicente.almacen.dto.ventas.VentaResponse;
import com.vicente.almacen.dto.ventas.VentasRequest;
import com.vicente.almacen.enums.EstadoVenta;

import java.util.List;

public interface VentaService {

    List<VentaResponse> listar();

    List<VentaResponse> listarCanceladas();

    VentaResponse obtenerPorIdActiva(long id);

    VentaResponse registrar(VentasRequest request);

    void  cancelar(long id);
}
