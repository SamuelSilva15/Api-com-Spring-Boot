package com.api.produtos.service.impl;

import com.api.produtos.exception.ProdutoNotFoundException;
import com.api.produtos.exception.QuantidadeInsuficienteException;
import com.api.produtos.model.Venda;
import com.api.produtos.service.ProdutoService;
import com.api.produtos.model.Produto;
import com.api.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

<<<<<<< Updated upstream
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
=======
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.List;
>>>>>>> Stashed changes

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Produto save(Produto produto) {
        produto.setAtivo(true);
        return produtoRepository.save(produto);
    }

    @Override
<<<<<<< Updated upstream
    public Page<Produto> findAll(int page, int size, String nome, BigDecimal quantidade) {
=======
    public Page<Produto> findAll(int page, int size, String nome, Integer quantidade) {
>>>>>>> Stashed changes
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setQuantidade(quantidade);

        return produtoRepository
                .findAll(Example.of(produto, exampleMatcher()), PageRequest.of(page, size, Sort.by("id")));
    }

    @Override
    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
    }
    @Override
    public void deleteById(Long id) {
        Produto produto = findById(id);
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

    @Override
    public Produto update(Long id, Produto produto) {
        findById(id);
        produto.setAtivo(true);
        return produtoRepository.save(produto);
    }


    public ExampleMatcher exampleMatcher() {
        return ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    }
}
