package com.vicente.almacen.repositories;

import com.vicente.almacen.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository //le dice que es la entidad que se encargadra de la comunicacion con la bd

//agregamos JpaSpecificationsExecutor ya que JpaRepository por si solo no ejecuta especificaciones
public interface ProductoRepository extends JpaRepository<Producto //jpaR.... sirve para hacer CRUD sin escribir ni una linea
        , Long>, JpaSpecificationExecutor<Producto> //sirve para hacer busquedas con filtros dinemicos opcionales
{
}

