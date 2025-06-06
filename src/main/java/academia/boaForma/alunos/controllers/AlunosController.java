package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.DadosAtualizar;
import academia.boaForma.alunos.dtos.DadosCadastroAluno;
import academia.boaForma.alunos.dtos.DadosDetalhamentoAlunos;
import academia.boaForma.alunos.dtos.DadosListarAlunos;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/alunos")
public class AlunosController {

    private final AlunosRepositorie alunosRepositorie;

    public AlunosController(AlunosRepositorie alunosRepositorie) {
        this.alunosRepositorie = alunosRepositorie;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DadosDetalhamentoAlunos> cadastroAluno(@RequestBody @Valid DadosCadastroAluno dadosCadastroAluno, UriComponentsBuilder uriBuilder) {
            var aluno = new AlunosModel(dadosCadastroAluno);
            alunosRepositorie.save(aluno);
            var uri = uriBuilder.path("/alunos/{id}").buildAndExpand(aluno.getId()).toUri();
            System.out.println(dadosCadastroAluno);
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
        var aluno = alunosRepositorie.getReferenceById(id);
        aluno.usuarioDesativadoDoSistema();
        return ResponseEntity.noContent().build();
    }
}
