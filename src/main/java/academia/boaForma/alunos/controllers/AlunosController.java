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
            @ApiResponse(responseCode = "422", description = "Dados para que possa ser salvo são inválidos")
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

    @GetMapping
    public Page<DadosListarAlunos> listarAlunos(Pageable paginacao) {
        var listagemAlunosAtivivos = alunosRepositorie.findAllAcessoSistema(paginacao).map(DadosListarAlunos::new);
        return ResponseEntity.ok(listagemAlunosAtivivos).getBody();
    }

    @GetMapping("/naoAtivos")
    public Page<DadosListarAlunos> listarAlunosNaoAtivos(Pageable paginacao) {
        var listagemAlunosNaoAtivivos = alunosRepositorie.findAllNaoAcessoSistema(paginacao).map(DadosListarAlunos::new);
        return ResponseEntity.ok(listagemAlunosNaoAtivivos).getBody();
    }

    @GetMapping("/all")
    public Page<DadosListarAlunos> listarTodosAlunos(Pageable paginacao) {
        var listagemTodosAlunos = alunosRepositorie.findAll(paginacao).map(DadosListarAlunos::new);
        return ResponseEntity.ok(listagemTodosAlunos).getBody();
    }

    @Transactional
    @PutMapping
    public ResponseEntity<DadosDetalhamentoAlunos> atualizarAluno(@RequestBody @Valid DadosAtualizar dadosAtualizar) {
        var aluno = alunosRepositorie.getReferenceById(dadosAtualizar.id());
        aluno.atualizarInformacoes(dadosAtualizar);
        alunosRepositorie.save(aluno);
        System.out.println(dadosAtualizar);
        return ResponseEntity.ok(new DadosDetalhamentoAlunos(aluno));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAlunos(@PathVariable Integer id) {
        var aluno = alunosRepositorie.alunoById(id);
        aluno.findAllAcessoSistema();
        alunosRepositorie.save(aluno);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/generos")
    public ResponseEntity<List<String>> listarGeneros() {
        return ResponseEntity.ok(Arrays.stream(Genero.values())
                .map(Genero::name)
                .collect(Collectors.toList()));
    }

    @GetMapping("/focoAluno")
    public ResponseEntity<List<String>> listarFocoAluno() {
        return ResponseEntity.ok(Arrays.stream(FocoAluno.values())
                .map(FocoAluno::name)
                .collect(Collectors.toList()));
    }

}
