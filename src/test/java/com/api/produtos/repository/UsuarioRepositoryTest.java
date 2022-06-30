package com.api.produtos.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    public void test(){
        String email = "moderador@email.com";
        repository.findByEmail(email);
    }
}
