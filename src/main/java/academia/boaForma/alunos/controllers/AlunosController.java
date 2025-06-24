package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.DadosAtualizar;
import academia.boaForma.alunos.dtos.DadosCadastroAluno;
import academia.boaForma.alunos.dtos.DadosDetalhamentoAlunos;
import academia.boaForma.alunos.dtos.DadosListarAlunos;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.models.informacoes.FocoAluno;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import academia.boaForma.professor.repositories.ProfessorRepositorie;
import academia.boaForma.usuarios.models.Genero;
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
public class AlunosController {

    private final AlunosRepositorie alunosRepositorie;
    private final ProfessorRepositorie professorRepositorie;

    public AlunosController(AlunosRepositorie alunosRepositorie, ProfessorRepositorie professorRepositorie) {
        this.alunosRepositorie = alunosRepositorie;
        this.professorRepositorie = professorRepositorie;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DadosDetalhamentoAlunos> cadastroAluno(@RequestBody @Valid DadosCadastroAluno dadosCadastroAluno, UriComponentsBuilder uriBuilder) {
        var professor = professorRepositorie.professorById(dadosCadastroAluno.professorResponsavelId());
        var aluno = new AlunosModel(dadosCadastroAluno);
        aluno.setProfessorResponsavelId(professor);
        alunosRepositorie.save(aluno);
        var uri = uriBuilder.path("/alunos/{id}").buildAndExpand(aluno.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoAlunos(aluno));
    }

    @GetMapping
    public Page<DadosListarAlunos> listarAlunos(Pageable paginacao) {
        var listagemAlunosAtivivos = alunosRepositorie.findAllAcessoSistema(paginacao).map(DadosListarAlunos::new);
        return ResponseEntity.ok(listagemAlunosAtivivos).getBody();
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
