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
        Produto samsung = produtoSamsung();
        Produto iphone = produtoIphone();

        Page page = new PageImpl(List.of(samsung, iphone));
        Mockito.when(produtoRepository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class))).thenReturn(page);

        //QUANDO
        Page<Produto> produtosPage = produtoService.findAll(0, 10, "TESTE", 1);

        //ENTÃO
        Assert.assertNotNull(produtosPage);
        Assert.assertEquals(2, produtosPage.getTotalElements());

        Assert.assertNotNull(produtosPage.getContent().get(0));
        Assert.assertEquals(Long.valueOf(1), produtosPage.getContent().get(0).getId());
        Assert.assertEquals("Samsung", produtosPage.getContent().get(0).getNome());
        Assert.assertEquals(20, produtosPage.getContent().get(0).getQuantidade().intValue());

        Assert.assertNotNull(produtosPage.getContent().get(1));
        Assert.assertEquals(Long.valueOf(2), produtosPage.getContent().get(1).getId());
        Assert.assertEquals("Iphone", produtosPage.getContent().get(1).getNome());
        Assert.assertEquals(10, produtosPage.getContent().get(1).getQuantidade().intValue());
    }

    @Test
    public void deveBuscarProdutoPorId() {
        //DADO
        Produto samsung = produtoSamsung();

        Mockito.when(produtoRepository.findById(samsung.getId())).thenReturn(Optional.of(samsung));

        //QUANDO
        Produto produto = produtoService.findById(1L);

        //ENTÃO
        Assert.assertNotNull(samsung);
        Assert.assertEquals(Long.valueOf(1), samsung.getId());
        Assert.assertEquals("Samsung", samsung.getNome());
        Assert.assertEquals(20, samsung.getQuantidade().intValue());
        Assert.assertEquals(120, samsung.getValor().intValue());
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
        Produto iphone = produtoIphone();

        Mockito.when(produtoRepository.save(iphone)).thenReturn(iphone);

        //QUANDO
        Produto produto = produtoService.save(iphone);

        //ENTÃO
        Assert.assertNotNull(produtoIphone());
        Assert.assertEquals(Long.valueOf(2), iphone.getId());
    }

    @Test
    public void deveAtualizarPorId(){
        //DADO
        Produto samsung = produtoSamsung();

        Mockito.when(produtoRepository.findById(2L)).thenReturn(Optional.of(samsung));


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
        Produto iphone = produtoIphone();

        Mockito.when(produtoRepository.findById(2L)).thenReturn(Optional.of(iphone));

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
