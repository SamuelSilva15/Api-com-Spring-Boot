package com.api.vendas.service;

import com.api.produtos.exception.QuantidadeInsuficienteException;
import com.api.produtos.model.Venda;

public interface VendaService {

    Venda venda(String token, Venda venda) throws QuantidadeInsuficienteException;
}
