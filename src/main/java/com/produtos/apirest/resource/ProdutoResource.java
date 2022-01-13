package com.produtos.apirest.resource;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import com.produtos.apirest.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.produtos.apirest.model.Produto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api/produtos")
@Api(value="API REST Produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;

	@ApiOperation(value="Salva um produto")
	@PostMapping
	public Produto save(@RequestBody @Valid Produto produto) {
		return produtoService.save(produto);
	}
	
	@ApiOperation(value="Retorna uma lista de produtos com paginação")
	@GetMapping
	public List<Produto> findAll(@RequestParam(required = false, defaultValue = "0") int page,
								 @RequestParam(required = false, defaultValue = Integer.MAX_VALUE + "") int size,
								 @RequestParam(required = false) String nome, @RequestParam(required = false) BigDecimal quantidade){
		return produtoService.findAll(page, size, nome, quantidade);
	}

	@ApiOperation(value="Retorna um produto por id")
	@GetMapping("/{id}")
	public Produto findById(@PathVariable Long id){
		return produtoService.findById(id);
	}

	@ApiOperation(value="Deleta um produto")
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		produtoService.deleteById(id);
	}
	
	@ApiOperation(value="Atualiza um produto")
	@PutMapping("/{id}")
	public Produto update(@PathVariable Long id, @RequestBody @Valid Produto produto) {
		return produtoService.update(id, produto);
	}
	 

}
