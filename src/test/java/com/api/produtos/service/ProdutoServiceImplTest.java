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
        produtoSamsung();

        //QUANDO
        List<Produto> produtos = List.of(produtoSamsung(), produtoIphone());
        Page page = new PageImpl(produtos);

        Mockito.when(produtoRepository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class))).thenReturn(page);

        Page produtosPage = produtoService.findAll(0, 10, "TESTE", 1);

        //ENTÃO
        Assert.assertNotNull(produtosPage);
        Assert.assertEquals(2, produtosPage.getTotalElements());
        Assert.assertNotNull(produtoSamsung());
        Assert.assertEquals(Long.valueOf(1), produtoSamsung().getId());
        Assert.assertEquals("Samsung", produtoSamsung().getNome());
        Assert.assertEquals(20, produtoSamsung().getQuantidade().intValue());

        Assert.assertNotNull(produtoIphone());
        Assert.assertEquals(Long.valueOf(2), produtoIphone().getId());
        Assert.assertEquals("Iphone", produtoIphone().getNome());
        Assert.assertEquals(10, produtoIphone().getQuantidade().intValue());
    }

    @Test
    public void deveBuscarProdutoPorId() {
        //DADO
        produtoIphone();

        Mockito.when(produtoRepository.findById(2L)).thenReturn(Optional.of(produtoIphone()));

        //QUANDO
        Produto produto = produtoService.findById(2L);

        //ENTÃO
        Assert.assertNotNull(produto);
        Assert.assertEquals(Long.valueOf(2), produtoIphone().getId());
        Assert.assertEquals("Iphone", produtoIphone().getNome());
        Assert.assertEquals(10, produtoIphone().getQuantidade().intValue());
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
        produtoIphone();

        Mockito.when(produtoRepository.save(produtoIphone())).thenReturn(produtoIphone());

        //QUANDO
        Produto produto = produtoService.save(produtoIphone());

        //ENTÃO
        Assert.assertNotNull(produtoIphone());
        Assert.assertEquals(Long.valueOf(2), produtoIphone().getId());
    }

    @Test
    public void deveAtualizarPorId(){
        //DADO
        produtoIphone();

        Mockito.when(produtoRepository.findById(2L)).thenReturn(Optional.of(produtoIphone()));


        //QUANDO
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(2L);
        produtoAtualizado.setNome("Samsung");
        produtoAtualizado.setQuantidade(12);
        produtoAtualizado.setValor(12);

        produtoService.update(2L, produtoAtualizado);

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
        produtoIphone();

        Mockito.when(produtoRepository.findById(2L)).thenReturn(Optional.of(produtoIphone()));

        //QUANDO
        produtoService.deleteById(2L);

        //ENTAO
        Mockito.verify(produtoRepository).save(captor.capture());
        Assert.assertFalse(captor.getValue().getAtivo());
    }

    public static Produto produtoSamsung(){
        Produto samsung = new Produto();
        samsung.setId(1L);
        samsung.setNome("Samsung");
        samsung.setQuantidade(20);
        samsung.setValor(120);
        return samsung;
    }

    public static Produto produtoIphone(){
        Produto iphone = new Produto();
        iphone.setId(2L);
        iphone.setNome("Iphone");
        iphone.setQuantidade(10);
        iphone.setValor(120);
        return iphone;
    }
}
