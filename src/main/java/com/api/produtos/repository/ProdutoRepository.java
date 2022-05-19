package com.api.produtos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.produtos.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
