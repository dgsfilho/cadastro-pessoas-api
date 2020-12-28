package br.eti.danielgomes.cadastropessoasapi.api.v1.resource;

import br.eti.danielgomes.cadastropessoasapi.api.v1.dto.PessoaDTO;
import br.eti.danielgomes.cadastropessoasapi.model.Pessoa;
import br.eti.danielgomes.cadastropessoasapi.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class PessoaControllerTest {

    static String PESSOA_API = "/api/v1/pessoas";

    private String json;
    private PessoaDTO dto;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PessoaService service;

    @BeforeEach
    void setUp() {
        this.json = getJsonPessoaDTO();

        this.dto = PessoaDTO.builder()
                .nome("Daniel Gomes da Silva Filho")
                .email("dgsfilho@gmail.com")
                .dataNascimento(getDataNascimento())
                .naturalidade("Barreiros - PE")
                .nacionalidade("Brasileira")
                .cpf("22233344405")
                .build();
    }

    @Test
    @DisplayName("Deve criar uma pessoa com sucesso.")
    public void createPessoaTest() throws Exception {

        Pessoa pessoa = Pessoa.builder()
                .nome("Daniel Gomes da Silva Filho")
                .email("dgsfilho@gmail.com")
                .dataNascimento(getDataNascimento())
                .naturalidade("Barreiros - PE")
                .nacionalidade("Brasileira")
                .cpf("22233344405")
                .id(1l)
                .build();

        BDDMockito.given(service.save(Mockito.any(Pessoa.class))).willReturn(pessoa);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PESSOA_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.json);

        mockMvc
                .perform(request)
                .andExpect( status().isCreated() )
                .andExpect( jsonPath("id").isNotEmpty() )
                .andExpect( jsonPath("nome").isNotEmpty() )
                .andExpect( jsonPath("dataNascimento").isNotEmpty() )
                .andExpect( jsonPath("cpf").isNotEmpty() )
                .andExpect( jsonPath("nome").value( this.dto.getNome() ) )
                .andExpect( jsonPath("email").value( this.dto.getEmail() ) )
                .andExpect( jsonPath("naturalidade").value( this.dto.getNaturalidade() ) )
                .andExpect( jsonPath("nacionalidade").value( this.dto.getNacionalidade() ) )
                .andExpect( jsonPath("cpf").value( this.dto.getCpf() ) )
                .andExpect( jsonPath("id").value( 1l ) )
        ;

    }

    @Test
    void findAll() {
    }

    @Test
    void getPessoaById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private String getJsonPessoaDTO() {
        return "{" +
                "\"id\": 1," +
                "\"nome\": \"Daniel Gomes da Silva Filho\"," +
                "\"email\": \"dgsfilho@gmail.com\"," +
                "\"dataNascimento\": \"2020-01-01\"," +
                "\"naturalidade\": \"Barreiros - PE\"," +
                "\"nacionalidade\": \"Brasileira\"," +
                "\"cpf\": \"22233344405\"," +
                "\"dataCadastro\": \"2020-12-23T19:45:28.469\"," +
                "\"dataAlteracao\": null" +
                "}";
    }

    private LocalDate getDataNascimento() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = "2020-01-01";
        return LocalDate.parse(date, formatter);
    }
}