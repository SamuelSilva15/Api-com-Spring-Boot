package com.produtos.apirest.service;

import com.produtos.apirest.model.Produto;

import java.util.List;

public interface ProdutoService {

     Produto save(Produto produto);
     List<Produto> findAll();
     Produto findById(Long id);
     void deleteById(Long id);
     Produto update(Long id, Produto produto);

}
