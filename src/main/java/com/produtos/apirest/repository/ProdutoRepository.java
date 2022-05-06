package com.produtos.apirest.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.produtos.apirest.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
