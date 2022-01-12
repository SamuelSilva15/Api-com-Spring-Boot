package com.produtos.apirest.resource;

import java.util.List;

import javax.validation.Valid;

import com.produtos.apirest.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@ApiOperation(value="Retorna uma lista de Produtos")
	@GetMapping
	public List<Produto> findAll(){
		return produtoService.findAll();
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
