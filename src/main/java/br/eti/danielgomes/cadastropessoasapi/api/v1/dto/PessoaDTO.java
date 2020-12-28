package br.eti.danielgomes.cadastropessoasapi.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {

    private Long id;
    @NotEmpty
    private String nome;
    private String email;
    @NotNull
    private LocalDate dataNascimento;
    private String naturalidade;
    private String nacionalidade;
    @NotEmpty
    private String cpf;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAlteracao;

}
