package com.example.demo.servico.impl;

import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.servico.PessoaService;
import com.example.demo.servico.exception.UnicidadeCpfException;
import com.example.demo.servico.exception.UnicidadeTelefoneException;

import java.util.Optional;

public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {

        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException {
        Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());

        if (optional.isPresent()) {
            throw new UnicidadeCpfException();
        }

        final String ddd = pessoa.getTelefones().get(0).getDdd();
        final String numero = pessoa.getTelefones().get(0).getNumero();
        optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(ddd, numero);

        if (optional.isPresent()) {
            throw new UnicidadeTelefoneException();
        }

        return pessoaRepository.save(pessoa);
    }
}
