package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.DadosCadastroAlunoDTO;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
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

/*    @Autowired
    private AlunosRepositorie alunosRepositorie;*/

    @Autowired
    private AlunoService alunoService;


/*    @PostMapping
    public void cadastroAluno(@RequestBody DadosCadastroAlunoDTO dadosCadastroAlunoDTO) {
        alunosRepositorie.save(new AlunosModel(dadosCadastroAlunoDTO));
        System.out.println(dadosCadastroAlunoDTO);
    }*/

    @PostMapping
    public ResponseEntity<Object> cadastroAluno(@RequestBody DadosCadastroAlunoDTO dadosCadastroAlunoDTO) {
        try {
            alunoService.cadastrarAluno(dadosCadastroAlunoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Aluno cadastrado com sucesso");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}
