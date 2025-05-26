package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.DadosCadastroAluno;
import academia.boaForma.alunos.services.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/alunos")
public class AlunosController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public ResponseEntity<Object> cadastroAluno(@RequestBody DadosCadastroAluno dadosCadastroAluno) {
        try {
            alunoService.cadastrarAluno(dadosCadastroAluno);
            return ResponseEntity.status(HttpStatus.CREATED).body("Aluno cadastrado com sucesso");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}
