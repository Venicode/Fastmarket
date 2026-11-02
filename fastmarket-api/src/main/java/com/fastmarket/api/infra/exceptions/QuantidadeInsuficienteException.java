package com.fastmarket.api.infra.exceptions;

public class QuantidadeInsuficienteException extends RuntimeException{
    public QuantidadeInsuficienteException(String message){
        super(message);
    }
}
