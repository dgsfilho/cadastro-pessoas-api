package br.eti.danielgomes.cadastropessoasapi.service;

import br.eti.danielgomes.cadastropessoasapi.model.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaService {

    Pessoa save(Pessoa pessoa);

    Optional<Pessoa> getById(Long id);

    Pessoa update(Pessoa pessoa);

    void delete(Pessoa pessoa);

    List<Optional<Pessoa>> findAll();

    Optional<Pessoa> getPessoaByCpf(String cpf);
}
