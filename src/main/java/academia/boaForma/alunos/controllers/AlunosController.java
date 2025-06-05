package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.DadosAtualizar;
import academia.boaForma.alunos.dtos.DadosCadastroAluno;
import academia.boaForma.alunos.dtos.DadosDetalhamentoAlunos;
import academia.boaForma.alunos.dtos.DadosListarAlunos;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import academia.boaForma.alunos.services.AlunosService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunosController {

    private final AlunosService alunosService;

    private final AlunosRepositorie alunosRepositorie;

    public AlunosController(AlunosService alunosService, AlunosRepositorie alunosRepositorie) {
        this.alunosService = alunosService;
        this.alunosRepositorie = alunosRepositorie;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<String> cadastroAluno(@RequestBody @Valid DadosCadastroAluno dadosCadastroAluno) {
            alunosService.cadastrarAluno(dadosCadastroAluno);
            System.out.println(dadosCadastroAluno);
            return ResponseEntity.status(HttpStatus.CREATED).body("Aluno cadastrado com sucesso");
    }

    @GetMapping
    public Page<DadosListarAlunos> listarAlunos(Pageable paginacao) {
        var listagemAlunosAtivivos = alunosRepositorie.findAllAcessoSistema(paginacao).map(DadosListarAlunos::new);
        return ResponseEntity.ok(listagemAlunosAtivivos).getBody();
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
