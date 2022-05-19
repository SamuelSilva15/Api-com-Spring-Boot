package com.produtos.apirest.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import com.produtos.apirest.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.produtos.apirest.model.Produto;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api/produtos")
@Api(value="API REST Produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@PostMapping
	public Produto save(@RequestBody @Valid Produto produto) {
		return produtoService.save(produto);
	}

	@GetMapping
	public Page<Produto> findAll(@RequestParam(required = false, defaultValue = "0") int page,
								 @RequestParam(required = false, defaultValue = Integer.MAX_VALUE + "")	int size,
								 @RequestParam(required = false) String nome,
								 @RequestParam(required = false) BigDecimal quantidade){
		return produtoService.findAll(page, size, nome, quantidade);
	}

	@GetMapping("/{id}")
	public Produto findById(@PathVariable Long id){
		return produtoService.findById(id);
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		produtoService.deleteById(id);
	}

	@PutMapping("/{id}")
	public Produto update(@PathVariable Long id, @RequestBody @Valid Produto produto) {
		return produtoService.update(id, produto);
	}
}
