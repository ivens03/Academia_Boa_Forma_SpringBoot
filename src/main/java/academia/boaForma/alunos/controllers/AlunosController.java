package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.DadosAtualizar;
import academia.boaForma.alunos.dtos.DadosCadastroAluno;
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
        return alunosRepositorie.findAllAcessoSistema(paginacao).map(DadosListarAlunos::new);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<String> atualizarAluno(@RequestBody @Valid DadosAtualizar dadosAtualizar) {
        var aluno = alunosRepositorie.getReferenceById(dadosAtualizar.id());
        aluno.atualizarInformacoes(dadosAtualizar);
        alunosRepositorie.save(aluno);
        System.out.println(dadosAtualizar);
        return ResponseEntity.status(HttpStatus.CREATED).body("Atualizado com sucesso");
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deletarAlunos(@PathVariable Integer id) {
        var aluno = alunosRepositorie.getReferenceById(id);
        aluno.usuarioDesativadoDoSistema();
    }
}
