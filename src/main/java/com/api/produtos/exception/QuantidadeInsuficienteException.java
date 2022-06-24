package com.api.produtos.exception;

import javax.persistence.NoResultException;

public class QuantidadeInsuficienteException extends Exception{

    public QuantidadeInsuficienteException(Integer quantidade){
        super("Quantidade insuficiente em estoque: " + quantidade);
    }
}
