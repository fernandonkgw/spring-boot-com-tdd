package com.example.demo.repository;

import com.example.demo.modelo.Pessoa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PessoaRepositoryTest {
//    Stub under test

    @Autowired
    private PessoaRepository sut;

    @Test
    public void deve_procurar_pessoa_pelo_cpf() throws Exception{
        final Optional<Pessoa> optional = sut.findByCpf("55565893569");

        assertThat(optional.isPresent()).isTrue();
        final Pessoa pessoa = optional.get();
        assertThat(pessoa.getCodigo()).isEqualTo(2L);
        assertThat(pessoa.getNome()).isEqualTo("Pedro");
        assertThat(pessoa.getCpf()).isEqualTo("55565893569");
    }

    @Test
    public void nao_deve_encontrar_pessoa_de_cpf_inexistente() {
        final Optional<Pessoa> optional = sut.findByCpf("2132312321321321");

        assertThat(optional.isPresent()).isFalse();
    }
}
