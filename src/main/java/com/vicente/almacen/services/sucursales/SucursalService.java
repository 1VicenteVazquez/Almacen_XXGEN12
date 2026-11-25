package com.vicente.almacen.services.sucursales;

import com.vicente.almacen.dto.sucursales.SucursalRequest;
import com.vicente.almacen.dto.sucursales.SucursalResponse;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;

public interface SucursalService {

    List<SucursalResponse> listar();

    SucursalResponse obtenerPorId(Long id);

    SucursalResponse registrar(SucursalRequest request);

    SucursalResponse actualizar(SucursalRequest request, Long id);

    void eliminar(Long id);
}
