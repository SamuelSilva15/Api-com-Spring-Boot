package com.api.vendas.controller;

import com.api.produtos.exception.QuantidadeInsuficienteException;
import com.api.produtos.model.Venda;
import com.api.vendas.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping("/vendas")
    public Venda venda(@RequestBody @Valid Venda venda, @RequestHeader("Authorization") String authorization) throws QuantidadeInsuficienteException {
        String token = authorization.split("Bearer ")[1];

        return vendaService.venda(token, venda);
    }
}
