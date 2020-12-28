package br.eti.danielgomes.cadastropessoasapi.repository;

import br.eti.danielgomes.cadastropessoasapi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    boolean existsByCpf( String cpf );

}
