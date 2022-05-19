package com.api.produtos.exception;

import javax.persistence.NoResultException;

public class ProdutoNotFoundException extends NoResultException {

    public ProdutoNotFoundException(Long id){
        super("Produto não encontrado: " + id);
    }
}
