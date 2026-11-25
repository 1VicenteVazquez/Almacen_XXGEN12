package com.vicente.almacen.exceptions;

public class DatoInvalidoException extends RuntimeException{
    public DatoInvalidoException(String mensaje){
        super(mensaje);
    }

}
