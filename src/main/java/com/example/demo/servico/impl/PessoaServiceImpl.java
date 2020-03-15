package com.example.demo.servico.impl;

import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.servico.PessoaService;

public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {

        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public void salvar(Pessoa pessoa) {

    }
}
