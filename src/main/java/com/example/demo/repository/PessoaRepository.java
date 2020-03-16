package com.example.demo.repository;

import com.example.demo.modelo.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByCpf(String cpf);

    Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(String ddd, String numero);
}
