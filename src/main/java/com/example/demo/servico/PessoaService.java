package com.example.demo.servico;

import com.example.demo.modelo.Pessoa;
import com.example.demo.servico.exception.UnicidadeCpfException;

public interface PessoaService {

    Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException;
}
