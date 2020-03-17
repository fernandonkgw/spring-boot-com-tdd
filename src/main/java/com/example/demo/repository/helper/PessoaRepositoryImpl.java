package com.example.demo.repository.helper;

import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.filtro.PessoaFiltro;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PessoaRepositoryImpl implements PessoaRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Pessoa> filtrar(PessoaFiltro filtro) {
        final StringBuilder sb = new StringBuilder();
        final Map<String, Object> params = new HashMap<>();

        sb.append("SELECT bean FROM Pessoa bean JOIN bean.telefones tele WHERE 1=1 ");

        preencheNome(filtro, sb, params);
        preencheCpf(filtro, sb, params);
        preencherDdd(filtro, sb, params);
        preencherTelefone(filtro, sb, params);

        final TypedQuery<Pessoa> query = manager.createQuery(sb.toString(), Pessoa.class);
        preencherParametrosDaQuery(params, query);

        return query.getResultList();
    }

    private void preencherTelefone(PessoaFiltro filtro, StringBuilder sb, Map<String, Object> params) {
        this.preencherSeNecessario(sb, params, filtro.getTelefone(), " AND tele.numero = :telefone ", "telefone", filtro.getTelefone());
    }

    private void preencherDdd(PessoaFiltro filtro, StringBuilder sb, Map<String, Object> params) {
        this.preencherSeNecessario(sb, params, filtro.getDdd(), " AND tele.ddd = :ddd ", "ddd", filtro.getDdd());
    }

    private void preencheCpf(PessoaFiltro filtro, StringBuilder sb, Map<String, Object> params) {
        this.preencherSeNecessario(sb, params, filtro.getCpf(), " AND bean.cpf LIKE :cpf ", "cpf", "%" + filtro.getCpf() + "%");
    }

    private void preencheNome(PessoaFiltro filtro, StringBuilder sb, Map<String, Object> params) {
        this.preencherSeNecessario(sb, params, filtro.getNome(), " AND bean.nome LIKE :nome ", "nome", "%" + filtro.getNome() + "%");
    }

    private void preencherSeNecessario(StringBuilder sb, Map<String, Object> params, String campoFiltro, String jpql, String key, String value) {
        if (StringUtils.hasText(campoFiltro)) {
            sb.append(jpql);
            params.put(key, value);
        }
    }

    private void preencherParametrosDaQuery(Map<String, Object> params, TypedQuery<Pessoa> query) {
        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
    }
}
