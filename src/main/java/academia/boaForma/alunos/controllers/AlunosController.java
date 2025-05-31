package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.DadosCadastroAluno;
import academia.boaForma.alunos.dtos.DadosListarAlunos;
import academia.boaForma.alunos.models.informacoes.Alunos;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunosController {

    private final AlunosRepositorie alunosRepositorie;

    public AlunosController(AlunosRepositorie alunosRepositorie) {
        this.alunosRepositorie = alunosRepositorie;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastroAluno(@RequestBody @Valid DadosCadastroAluno dadosCadastroAluno) {
            alunosRepositorie.save(new Alunos(dadosCadastroAluno));
            System.out.println(dadosCadastroAluno);
            return ResponseEntity.status(HttpStatus.CREATED).body("Aluno cadastrado com sucesso");
    }

    @GetMapping
    public List<DadosListarAlunos> listarAlunos() {
        return alunosRepositorie.findAll().stream().map(DadosListarAlunos::new).toList();
    }

}
