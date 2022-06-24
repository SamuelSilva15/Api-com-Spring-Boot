package com.api.produtos.service;


import com.api.produtos.model.Produto;
import org.springframework.data.domain.Page;


public interface ProdutoService {

<<<<<<< Updated upstream
=======
     Page<Produto> findAll(int page, int size, String nome, Integer quantidade);

>>>>>>> Stashed changes
     Produto save(Produto produto);
     Page<Produto> findAll(int page, int size, String nome, BigDecimal quantidade);
     Produto findById(Long id);
     void deleteById(Long id);
     Produto update(Long id, Produto produto);




}
