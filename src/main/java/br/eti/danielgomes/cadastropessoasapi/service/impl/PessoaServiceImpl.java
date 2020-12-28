package br.eti.danielgomes.cadastropessoasapi.service.impl;

import br.eti.danielgomes.cadastropessoasapi.model.Pessoa;
import br.eti.danielgomes.cadastropessoasapi.repository.PessoaRepository;
import br.eti.danielgomes.cadastropessoasapi.service.PessoaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository repository;

    public PessoaServiceImpl(PessoaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pessoa save(Pessoa pessoa) {
        if (this.repository.existsByCpf(pessoa.getCpf())) {
            throw new IllegalArgumentException("O CPF informado já está cadastrado.");
        }
        return this.repository.save(pessoa);
    }

    @Override
    public Optional<Pessoa> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Pessoa update(Pessoa pessoa) {
        Optional<Pessoa> optionalPessoa = this.repository.findById(pessoa.getId());
        Pessoa pessoaCadastrada = optionalPessoa.get();
        String cpfAtual = pessoaCadastrada.getCpf();

        if (pessoa == null || pessoa.getId() == null) {
            throw new IllegalArgumentException("O id da pessoa deve ser informado.");
        } else if (!cpfAtual.equals(pessoa.getCpf()) && this.repository.existsByCpf(pessoa.getCpf())) {
            throw new IllegalArgumentException("O CPF informado já está cadastrado.");
        }

        return this.repository.save(pessoa);
    }

    @Override
    public void delete(Pessoa pessoa) {
        if(pessoa == null || pessoa.getId() == null) {
            throw new IllegalArgumentException("O id da pessoa deve ser informado.");
        }
        this.repository.delete(pessoa);
    }

    @Override
    public List<Optional<Pessoa>> findAll() {
        return converteOptional(this.repository.findAll());
    }

    @Override
    public Optional<Pessoa> getPessoaByCpf(String cpf) {
        return Optional.empty();
    }

    private List<Optional<Pessoa>> converteOptional(List<Pessoa> listPessoa) {
        List<Optional<Pessoa>> listOptionals = new ArrayList<>();
        for(Pessoa pessoa: listPessoa) {
            listOptionals.add(Optional.of(pessoa));
        }
        return listOptionals;
    }
}
