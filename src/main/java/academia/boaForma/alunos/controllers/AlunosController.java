package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.alunosDtos.DadosAtualizar;
import academia.boaForma.alunos.dtos.alunosDtos.DadosCadastroAluno;
import academia.boaForma.alunos.dtos.alunosDtos.DadosDetalhamentoAlunos;
import academia.boaForma.alunos.dtos.alunosDtos.DadosListarAlunos;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.models.informacoes.FocoAluno;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import academia.boaForma.professor.repositories.ProfessorRepositorie;
import academia.boaForma.usuarios.models.Genero;
import academia.boaForma.usuarios.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos", description = "Controller Responsavel Pelo Alunos")
public class AlunosController {

    private final AlunosRepositorie alunosRepositorie;
    private final ProfessorRepositorie professorRepositorie;
    private final UsuarioService usuarioService;

    public AlunosController(AlunosRepositorie alunosRepositorie, ProfessorRepositorie professorRepositorie, UsuarioService usuarioService) {
        this.alunosRepositorie = alunosRepositorie;
        this.professorRepositorie = professorRepositorie;
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Cadastrar alunos",description = "Metado para fazer o cadastro de um aluno dentro do sistema da academia", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Erro ao salvar"),
            @ApiResponse(responseCode = "422", description = "Dados para que possa ser salvo são inválidos"),
            @ApiResponse(responseCode = "404", description = "Erro no servidor")
    })
    @Transactional
    @PostMapping
    public ResponseEntity<DadosDetalhamentoAlunos> cadastroAluno(@RequestBody @Valid DadosCadastroAluno dadosCadastroAluno, UriComponentsBuilder uriBuilder) {
        var professor = professorRepositorie.professorById(dadosCadastroAluno.professorResponsavelId());
        var aluno = new AlunosModel(dadosCadastroAluno);
        aluno.setProfessorResponsavelId(professor);
        var alunoSalvo = usuarioService.cadastrar(aluno);
        var uri = uriBuilder.path("/alunos/{id}").buildAndExpand(alunoSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoAlunos((AlunosModel) alunoSalvo));
    }

    @Operation(summary = "Listar alunos",description = "Metado para listar alunos com acesso ao sistema", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Erro ao buscar alunos"),
            @ApiResponse(responseCode = "404", description = "Erro no servidor")
    })
    @GetMapping
    public Page<DadosListarAlunos> listarAlunos(Pageable paginacao) {
        var listagemAlunosAtivivos = alunosRepositorie.findAllAcessoSistema(paginacao).map(DadosListarAlunos::new);
        return ResponseEntity.ok(listagemAlunosAtivivos).getBody();
    }

    @Operation(summary = "Listar alunos",description = "Metado para listar alunos sem acesso ao sistema", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Erro ao buscar alunos"),
            @ApiResponse(responseCode = "404", description = "Erro no servidor")
    })
    @GetMapping("/naoAtivos")
    public Page<DadosListarAlunos> listarAlunosNaoAtivos(Pageable paginacao) {
        var listagemAlunosNaoAtivivos = alunosRepositorie.findAllNaoAcessoSistema(paginacao).map(DadosListarAlunos::new);
        return ResponseEntity.ok(listagemAlunosNaoAtivivos).getBody();
    }

    @Operation(summary = "Listar todos alunos",description = "Metado para listar todos alunos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Erro ao buscar alunos"),
            @ApiResponse(responseCode = "404", description = "Erro no servidor")
    })
    @GetMapping("/all")
    public Page<DadosListarAlunos> listarTodosAlunos(Pageable paginacao) {
        var listagemTodosAlunos = alunosRepositorie.findAll(paginacao).map(DadosListarAlunos::new);
        return ResponseEntity.ok(listagemTodosAlunos).getBody();
    }

    @Operation(summary = "Atualizar alunos",description = "Metado para atualizar alunos", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Erro ao buscar alunos"),
            @ApiResponse(responseCode = "404", description = "Erro no servidor"),
            @ApiResponse(responseCode = "409", description = "ja cadastrado")
    })
    @Transactional
    @PutMapping
    public ResponseEntity<DadosDetalhamentoAlunos> atualizarAluno(@RequestBody @Valid DadosAtualizar dadosAtualizar) {
        var aluno = alunosRepositorie.getReferenceById(dadosAtualizar.id());
        aluno.atualizarInformacoes(dadosAtualizar);
        alunosRepositorie.save(aluno);
        System.out.println(dadosAtualizar);
        return ResponseEntity.ok(new DadosDetalhamentoAlunos(aluno));
    }

    @Operation(summary = "Delete logico dos alunos",description = "Metado para desativar um aluno, sem deletar do banco de dados", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Erro ao buscar alunos"),
            @ApiResponse(responseCode = "404", description = "Erro no servidor")
    })
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAlunos(@PathVariable Integer id) {
        var aluno = alunosRepositorie.alunoById(id);
        aluno.findAllAcessoSistema();
        alunosRepositorie.save(aluno);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar generos",description = "Metado para listar generos disponivel no ENUM", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Erro ao buscar alunos"),
            @ApiResponse(responseCode = "404", description = "Erro no servidor")
    })
    @GetMapping("/generos")
    public ResponseEntity<List<String>> listarGeneros() {
        return ResponseEntity.ok(Arrays.stream(Genero.values())
                .map(Genero::name)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Listar generos",description = "Metado para listar objetivos do aluno no ENUM", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Erro ao buscar alunos"),
            @ApiResponse(responseCode = "404", description = "Erro no servidor")
    })
    @GetMapping("/focoAluno")
    public ResponseEntity<List<String>> listarFocoAluno() {
        return ResponseEntity.ok(Arrays.stream(FocoAluno.values())
                .map(FocoAluno::name)
                .collect(Collectors.toList()));
    }

}
