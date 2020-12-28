package br.eti.danielgomes.cadastropessoasapi.api.v2.resource;

import br.eti.danielgomes.cadastropessoasapi.api.exception.ResourceNotFoundException;
import br.eti.danielgomes.cadastropessoasapi.api.v2.dto.PessoaDTO;
import br.eti.danielgomes.cadastropessoasapi.model.Pessoa;
import br.eti.danielgomes.cadastropessoasapi.service.PessoaService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController("PessoaControllerV2")
@RequestMapping("/api/v2/pessoas")
public class PessoaController {

    private final PessoaService service;
    private final ModelMapper modelMapper;

    public PessoaController(PessoaService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaDTO create(@RequestBody @Valid PessoaDTO dto) {

        Pessoa entity = this.modelMapper.map(dto, Pessoa.class);
        entity.setDataCadastro(LocalDateTime.now());
        entity.setDataAlteracao(null);
        entity = this.service.save(entity);

        return this.modelMapper.map(entity, PessoaDTO.class);

    }

    @GetMapping
    public List<Optional<PessoaDTO>> findAll() {
        List<Optional<Pessoa>> list = this.service.findAll();
        List<Optional<PessoaDTO>> result = new ArrayList<>();
        for(Optional<Pessoa> opt: list) {
            Pessoa entity;
            entity = opt.get();
            result.add(Optional.of(this.modelMapper.map(entity, PessoaDTO.class)));
        }
        return result;
    }

    @GetMapping("/{id}")
    public PessoaDTO getPessoaById(@PathVariable Long id) {
        Pessoa pessoa = this.service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa inexistente. Id: " + id));

        return this.modelMapper.map(pessoa, PessoaDTO.class);
    }

    @PutMapping("{id}")
    public PessoaDTO update(@PathVariable Long id, @RequestBody @Valid PessoaDTO dto){
        return this.service.getById(id).map(pessoa -> {
            pessoa.setNome(dto.getNome());
            pessoa.setEmail(dto.getEmail());
            pessoa.setDataNascimento(dto.getDataNascimento());
            pessoa.setNaturalidade(dto.getNaturalidade());
            pessoa.setNacionalidade(dto.getNacionalidade());
            pessoa.setCpf(dto.getCpf());
            pessoa.setDataAlteracao(LocalDateTime.now());
            pessoa = service.update(pessoa);
            return this.modelMapper.map(pessoa, PessoaDTO.class);
        }).orElseThrow( () -> new ResourceNotFoundException("Pessoa inexistente. Id: " + id) );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Pessoa pessoa = this.service.getById(id).orElseThrow(() ->
                new ResourceNotFoundException("Pessoa inexistente. Id: " + id));
        this.service.delete(pessoa);
    }

}

