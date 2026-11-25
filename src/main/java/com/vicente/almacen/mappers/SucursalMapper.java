package com.vicente.almacen.mappers;


import com.vicente.almacen.dto.sucursales.SucursalRequest;
import com.vicente.almacen.dto.sucursales.SucursalResponse;
import com.vicente.almacen.entities.Sucursal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;

@Component

public class SucursalMapper {
    public Sucursal requestAEntidad(SucursalRequest request){
        return  request == null
                ? null
                : Sucursal.crear(request.nombre(), request.direccion());
    }

    public SucursalResponse entidadAResponse(Sucursal sucursal){
        return  sucursal == null
                ? null
                : new SucursalResponse(
                sucursal.getId(),
                sucursal.getNombre(),
                sucursal.getDireccion());
    }

}