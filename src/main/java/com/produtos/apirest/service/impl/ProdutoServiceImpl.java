package com.produtos.apirest.service.impl;

import com.produtos.apirest.model.Produto;
import com.produtos.apirest.repository.ProdutoRepository;
import com.produtos.apirest.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;


    @Override
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public List<Produto> findAll(int page, int size, String nome, BigDecimal quantidade) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setQuantidade(quantidade);

        return produtoRepository
                .findAll(Example.of(produto, exampleMatcher()), PageRequest.of(page, size, Sort.by("id")))
                .getContent();
    }

    @Override
    public Produto findById(Long id) {
        return produtoRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public Produto update(Long id, Produto produto) {
        produto.setId(id);
        return produtoRepository.save(produto);
    }

    public ExampleMatcher exampleMatcher() {
        return ExampleMatcher
                .matching()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    }
}
