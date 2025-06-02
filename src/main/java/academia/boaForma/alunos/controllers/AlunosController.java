package academia.boaForma.alunos.controllers;

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

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastroAluno(@RequestBody @Valid DadosCadastroAluno dadosCadastroAluno) {
            alunosService.cadastrarAluno(dadosCadastroAluno);
            System.out.println(dadosCadastroAluno);
            return ResponseEntity.status(HttpStatus.CREATED).body("Aluno cadastrado com sucesso");
    }

    @GetMapping
    public Page<DadosListarAlunos> listarAlunos(Pageable paginacao) {
        return alunosRepositorie.findAll(paginacao).map(DadosListarAlunos::new);
    }

}
