package com.api.vendas.service.impl;

import com.api.config.security.TokenService;
import com.api.produtos.exception.QuantidadeInsuficienteException;
import com.api.produtos.model.Produto;
import com.api.produtos.model.Usuario;
import com.api.produtos.model.Venda;
import com.api.produtos.repository.UsuarioRepository;
import com.api.produtos.service.ProdutoService;
import com.api.vendas.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class VendaServiceImpl implements VendaService {

    private ProdutoService produtoService;
    private UsuarioRepository usuarioRepository;
    private TokenService tokenService;

    @Autowired
    public VendaServiceImpl(ProdutoService produtoService, UsuarioRepository usuarioRepository, TokenService tokenService){
        this.produtoService = produtoService;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    @Override
    public Venda venda(String token, Venda venda) throws QuantidadeInsuficienteException {
        Produto produto = produtoService.findById(venda.getProduto());
        produto.diminuirQuantidade(venda.getQuantidade());
        produtoService.save(produto);

        BigDecimal valor = BigDecimal.valueOf(produto.getValor()).multiply(BigDecimal.valueOf(venda.getQuantidade()));
        venda.setValor(valor);
        venda.setUsuario(getUsuarioLogado(token).getEmail());
        return venda;
    }

    private Usuario getUsuarioLogado(String token) {
        Long idUsuario = tokenService.getIdUsuario(token);

        return usuarioRepository.findById(idUsuario)
                .orElse(new Usuario());
    }

}
