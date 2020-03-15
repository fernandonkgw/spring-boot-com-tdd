package com.example.demo.repository;

import com.example.demo.modelo.Pessoa;

import java.util.Optional;

public interface PessoaRepository {
    Pessoa save(Pessoa pessoa);

    Optional<Pessoa> findByCpf(String cpf);
}
