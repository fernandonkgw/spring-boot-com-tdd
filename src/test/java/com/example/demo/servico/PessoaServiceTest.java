package com.example.demo.servico;

import com.example.demo.modelo.Pessoa;
import com.example.demo.modelo.Telefone;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.servico.exception.TelefoneNaoEncontradoException;
import com.example.demo.servico.exception.UnicidadeCpfException;
import com.example.demo.servico.exception.UnicidadeTelefoneException;
import com.example.demo.servico.impl.PessoaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

    private static final String NOME = "Fernando Nakagawa";
    private static final String CPF = "12345678912";
    private static final String DDD = "11";
    private static final String NUMERO = "1232312313";
    @MockBean
    private PessoaRepository pessoaRepository;
    private PessoaService sut;
    private Pessoa pessoa;
    private Telefone telefone;

    @Before
    public void setUp() {
        sut = new PessoaServiceImpl(pessoaRepository);
        pessoa = new Pessoa();
        pessoa.setNome(NOME);
        pessoa.setCpf(CPF);

        telefone = new Telefone();
        telefone.setDdd(DDD);
        telefone.setNumero(NUMERO);

        pessoa.setTelefones(Arrays.asList(telefone));

//        Nó vídeo orienta colocar o when mas nos testes não houve necessidade
//        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());

    }

    @Test
    public void deve_salvar_pessoa_no_repositorio() throws Exception {
        sut.salvar(pessoa);

        verify(pessoaRepository).save(pessoa);
    }

    @Test(expected = UnicidadeCpfException.class)
    public void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() throws Exception {
        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));

        sut.salvar(pessoa);
    }

    @Test(expected = UnicidadeTelefoneException.class)
    public void nao_deve_salvar_duas_pessoas_com_o_mesmo_telefone() throws Exception {
        when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

        sut.salvar(pessoa);
    }

    @Test(expected = TelefoneNaoEncontradoException.class)
    public void deve_retornar_excecao_de_nao_encontrado_quando_nao_existir_pessoa_com_o_ddd_e_numero_de_telefone() throws Exception {
        sut.buscarPorTelefone(telefone);
    }

    @Test
    public void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() throws Exception {
        when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

        Pessoa pessoaTeste = sut.buscarPorTelefone(telefone);

        verify(pessoaRepository).findByTelefoneDddAndTelefoneNumero(DDD, NUMERO);

        assertThat(pessoaTeste).isNotNull();
        assertThat(pessoaTeste.getNome()).isEqualTo(NOME);
        assertThat(pessoaTeste.getCpf()).isEqualTo(CPF);
    }
}
