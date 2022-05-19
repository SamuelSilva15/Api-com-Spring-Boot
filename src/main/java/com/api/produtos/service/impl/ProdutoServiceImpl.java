package com.api.produtos.service.impl;

import com.api.produtos.service.ProdutoService;
import com.api.produtos.model.Produto;
import com.api.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Produto save(Produto produto) {
        produto.setStatus(true);
        return produtoRepository.save(produto);
    }

    @Override
    public Page<Produto> findAll(int page, int size, String nome, BigDecimal quantidade) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setQuantidade(quantidade);

        return produtoRepository
                .findAll(Example.of(produto, exampleMatcher()), PageRequest.of(page, size, Sort.by("id")));
    }

    @Override
    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        Produto produto = produtoRepository.findById(id).get();
        produto.setStatus(false);
        produtoRepository.deleteById(id);
    }

    @Override
    public Produto update(Long id, Produto produto) {
        produtoRepository.findById(id).get();
        produto.setStatus(true);
        return produtoRepository.save(produto);
    }

    public ExampleMatcher exampleMatcher() {
        return ExampleMatcher
                .matching()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnoreNullValues();
    }
}
