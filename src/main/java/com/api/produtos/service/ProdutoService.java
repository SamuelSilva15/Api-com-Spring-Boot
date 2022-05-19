package com.api.produtos.service;

import com.api.produtos.model.Produto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface ProdutoService {

     Produto save(Produto produto);
     Page<Produto> findAll(int page, int size, String nome, BigDecimal quantidade);
     Produto findById(Long id);
     void deleteById(Long id);
     Produto update(Long id, Produto produto);



}
