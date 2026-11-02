package com.fastmarket.api.infra.exceptions;

public class ValorInsuficienteException extends RuntimeException{
    public ValorInsuficienteException(String message){
        super(message);
    }
}
