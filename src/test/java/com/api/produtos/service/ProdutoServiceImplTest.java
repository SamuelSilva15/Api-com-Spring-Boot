package com.api.produtos.service;

import com.api.produtos.exception.ProdutoNotFoundException;
import com.api.produtos.model.Produto;
import com.api.produtos.repository.ProdutoRepository;
import com.api.produtos.service.impl.ProdutoServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProdutoServiceImplTest {

    @Captor
    private ArgumentCaptor<Produto> captor = ArgumentCaptor.forClass(Produto.class);

    private ProdutoRepository produtoRepository;
    private ProdutoService produtoService;

    @Before
    public void setup(){
        produtoRepository = Mockito.mock(ProdutoRepository.class);
        produtoService = new ProdutoServiceImpl(produtoRepository);
    }

    @Test
    public void deveBuscarTodosProdutos(){
        //DADO
        Produto samsung = new Produto();
        samsung.setId(1L);
        samsung.setNome("Samsung");
        samsung.setQuantidade(20);
        samsung.setValor(120);

        Produto iphone = new Produto();
        iphone.setId(2L);
        iphone.setNome("Iphone");
        iphone.setQuantidade(10);
        iphone.setValor(120);

        //QUANDO
        List<Produto> produtos = List.of(samsung, iphone);
        Page page = new PageImpl(produtos);

        Mockito.when(produtoRepository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class))).thenReturn(page);

        Page produtosPage = produtoService.findAll(0, 10, "TESTE", 1);

        //ENTÃO
        Assert.assertNotNull(produtosPage);
        Assert.assertEquals(2, produtosPage.getTotalElements());
        Assert.assertNotNull(samsung);
        Assert.assertEquals(Long.valueOf(1), samsung.getId());
        Assert.assertEquals("Samsung", samsung.getNome());
        Assert.assertEquals(20, samsung.getQuantidade().intValue());

        Assert.assertNotNull(iphone);
        Assert.assertEquals(Long.valueOf(2), iphone.getId());
        Assert.assertEquals("Iphone", iphone.getNome());
        Assert.assertEquals(10, iphone.getQuantidade().intValue());
    }

    @Test
    public void deveBuscarProdutoPorId() {
        //DADO
        Produto produtoEsperado = new Produto();
        produtoEsperado.setId(1L);
        produtoEsperado.setNome("Iphone");
        produtoEsperado.setQuantidade(20);
        produtoEsperado.setValor(120);

        Mockito.when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoEsperado));

        //QUANDO
        Produto produto = produtoService.findById(1L);

        //ENTÃO
        Assert.assertNotNull(produto);
        Assert.assertEquals(Long.valueOf(1), produto.getId());
        Assert.assertEquals("Iphone", produto.getNome());
        Assert.assertEquals(20, produto.getQuantidade().intValue());
    }

    @Test(expected = ProdutoNotFoundException.class)
    public void deveLancarProdutoNotFoundExceptionQuandoOProdutoNaoForEncontrado(){
        //DADO
        Mockito.when(produtoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        //QUANDO
        produtoService.findById(4L);
    }

    @Test
    public void deveSalvarProduto(){
        //DADO
        Produto iphone = new Produto();
        iphone.setId(1L);

        Mockito.when(produtoRepository.save(iphone)).thenReturn(iphone);

        //QUANDO
        Produto produto = produtoService.save(iphone);

        //ENTÃO
        Assert.assertNotNull(produto);
        Assert.assertEquals(Long.valueOf(1), produto.getId());
    }

    @Test
    public void deveAtualizarPorId(){
        //DADO
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Iphone");
        produto.setQuantidade(12);
        produto.setValor(10);
        produto.setAtivo(Boolean.FALSE);
        Mockito.when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));


        //QUANDO
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(1L);
        produtoAtualizado.setNome("Samsung");
        produtoAtualizado.setQuantidade(12);
        produtoAtualizado.setValor(12);

        produtoService.update(1L, produtoAtualizado);

        //ENTAO
        Mockito.verify(produtoRepository).save(captor.capture());
        Assert.assertEquals("Samsung", captor.getValue().getNome());
        Assert.assertEquals(12, captor.getValue().getValor().intValue());
        Assert.assertEquals(12, captor.getValue().getQuantidade().intValue());
        Assert.assertEquals(Boolean.TRUE, captor.getValue().getAtivo());
    }

    @Test
    public void deveExcluirPorId(){
        //DADO
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Iphone");
        produto.setQuantidade(12);
        produto.setValor(10);
        produto.setAtivo(Boolean.TRUE);

        Mockito.when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        //QUANDO
        produtoService.deleteById(1L);

        //ENTAO
        Mockito.verify(produtoRepository).save(captor.capture());
        Assert.assertFalse(captor.getValue().getAtivo());
    }
}
