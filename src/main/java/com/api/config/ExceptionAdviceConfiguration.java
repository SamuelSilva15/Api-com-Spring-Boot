package com.api.config;


import com.api.produtos.exception.QuantidadeInsuficienteException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;

@RestControllerAdvice
public class ExceptionAdviceConfiguration {

    @ResponseBody
    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noResultExceptionHandler(NoResultException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(QuantidadeInsuficienteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String quantidadeInsuficienteExceptionHandler(QuantidadeInsuficienteException e) {
        return e.getMessage();
    }

}
