package com.example.demo.servico;

import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.servico.impl.PessoaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

    private static final String NOME = "Fernando Nakagawa";
    private static final String CPF = "12345678912";
    @MockBean
    private PessoaRepository pessoaRepository;
    private PessoaService sut;
    private Pessoa pessoa;

    @Before
    public void setUp() {
        sut = new PessoaServiceImpl(pessoaRepository);
        pessoa = new Pessoa();
        pessoa.setNome(NOME);
        pessoa.setCpf(CPF);
    }

    @Test
    public void deve_salvar_pessoa_no_repositorio() throws Exception {
        sut.salvar(pessoa);

        verify(pessoaRepository).save(pessoa);
    }
}
