package com.api.produtos.service.impl;

import com.api.produtos.exception.ProdutoNotFoundException;
import com.api.produtos.service.ProdutoService;
import com.api.produtos.model.Produto;
import com.api.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Page<Produto> findAll(int page, int size, String nome, int quantidade) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setQuantidade(quantidade);

        return produtoRepository
                .findAll(Example.of(produto, exampleMatcher()), PageRequest.of(page, size, Sort.by("id")));
    }

    @Override
    public Produto save(Produto produto) {
        produto.setAtivo(true);
        return produtoRepository.save(produto);
    }

    @Override
    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
    }
    @Override
    public Produto deleteById(Long id) {
        Produto produto = findById(id);
        produto.setAtivo(false);
        produtoRepository.save(produto);
        return produto;
    }

    @Override
    public Produto update(Long id, Produto produto) {
        Produto atual = findById(id);
        atual.setNome(produto.getNome());
        atual.setQuantidade(produto.getQuantidade());
        atual.setValor(produto.getValor());
        atual.setAtivo(true);
        return produtoRepository.save(atual);
    }

    public ExampleMatcher exampleMatcher() {
        return ExampleMatcher
                .matching()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnoreNullValues();
    }
}
