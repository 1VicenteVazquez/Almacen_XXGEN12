package com.vicente.almacen.services.sucursales;

import com.vicente.almacen.dto.sucursales.SucursalRequest;
import com.vicente.almacen.dto.sucursales.SucursalResponse;
import com.vicente.almacen.entities.Sucursal;
import com.vicente.almacen.exceptions.ConflictoException;
import com.vicente.almacen.exceptions.RecursoNoEncontradoException;
import com.vicente.almacen.mappers.SucursalMapper;
import com.vicente.almacen.repositories.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SucursalServiceImpl implements SucursalService {

    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponse> listar() {
        log.info("Listando todas las sucursales......");

        return sucursalRepository.findAll().stream()
                .map(sucursalMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SucursalResponse obtenerPorId(Long id) {
        return sucursalMapper.entidadAResponse(obtenerSucursalPorId(id));
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {
        log.info("Registrando sucursal....");

        validarDatosUnicos(request);

        Sucursal sucursal = sucursalMapper.requestAEntidad(request);
        sucursalRepository.saveAndFlush(sucursal);

        log.info("Nueva sucursal {} registrada", sucursal.getNombre());

        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id) {
        log.info("Actualizando sucursal con id: {}", id);

        Sucursal sucursal = obtenerSucursalPorId(id);
        validarCambiosUnicos(request, id);

        sucursal.actualizar(
                request.nombre(),
                request.direccion());

        sucursalRepository.saveAndFlush(sucursal);
        log.info("Sucursal {} actualizada", sucursal.getNombre());

        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public void eliminar(Long id) {
        Sucursal sucursal = obtenerSucursalPorId(id);
        log.info("Eliminando sucursal con id: {}", id);

        sucursalRepository.delete(sucursal);
        sucursalRepository.flush();

        log.info("Sucursal con id {} eliminada", id);
    }

    private Sucursal obtenerSucursalPorId(Long id) {
        log.info("Buscando sucursal con id: {}", id);

        return sucursalRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Sucursal no encontrada con id: " + id));
    }

    private void validarDatosUnicos(SucursalRequest request) {
        log.info("Validando nombre unico...");

        if (sucursalRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new ConflictoException(
                    "Ya existe una sucursal con el nombre: " + request.nombre());
    }

    private void validarCambiosUnicos(SucursalRequest request, Long id) {
        log.info("Validando cambio de nombre unico....");

        if (sucursalRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new ConflictoException(
                    "Ya existe una sucursal con el nombre: " + request.nombre());
    }
}