package com.example.demo.repository;

import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.filtro.PessoaFiltro;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
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

    @Test
    public void deve_encontrar_pessoa_pelo_ddd_e_numero_de_telefone() {
        final Optional<Pessoa> optional = sut.findByTelefoneDddAndTelefoneNumero("82", "39945903");

        assertThat(optional.isPresent()).isTrue();
        final Pessoa pessoa = optional.get();
        assertThat(pessoa.getCodigo()).isEqualTo(2L);
        assertThat(pessoa.getNome()).isEqualTo("Pedro");
        assertThat(pessoa.getCpf()).isEqualTo("55565893569");
    }

    @Test
    public void nao_deve_encontrar_pessoa_cujo_ddd_e_telefone_nao_estejam_cadastrados() {
        final Optional<Pessoa> optional = sut.findByTelefoneDddAndTelefoneNumero("23", "3454354353");

        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    public void deve_filtrar_pessoas_por_parte_do_nome() {
        final PessoaFiltro filtro = new PessoaFiltro();
        filtro.setNome("a");

        List<Pessoa> pessoas = sut.filtrar(filtro);

        assertThat(pessoas.size()).isEqualTo(3);
    }

    @Test
    public void deve_filtrar_pessoas_por_parte_cpf() {
        final PessoaFiltro filtro = new PessoaFiltro();
        filtro.setCpf("78");

        List<Pessoa> pessoas = sut.filtrar(filtro);

        assertThat(pessoas.size()).isEqualTo(3);
    }

    @Test
    public void deve_filtrar_pessoas_por_filtro_composto() {
        final PessoaFiltro filtro = new PessoaFiltro();
        filtro.setNome("a");
        filtro.setCpf("78");

        final List<Pessoa> pessoas = sut.filtrar(filtro);

        assertThat(pessoas.size()).isEqualTo(2);
    }

    @Test
    public void deve_filtrar_pessoas_pelo_ddd_do_telefone() {
        final PessoaFiltro filtro = new PessoaFiltro();
        filtro.setDdd("21");

        final List<Pessoa> pessoas = sut.filtrar(filtro);

        assertThat(pessoas.size()).isEqualTo(1);
    }

    @Test
    public void deve_filtrar_pessoas_pelo_numero_do_telefone() {
        final PessoaFiltro filtro = new PessoaFiltro();
        filtro.setTelefone("21321312");

        final List<Pessoa> pessoas = sut.filtrar(filtro);

        assertThat(pessoas.size()).isEqualTo(0);
    }
}
