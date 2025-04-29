package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.AlunosDto;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AlunosController {

    @Autowired
    AlunosRepositorie alunosRepositorie;

    @PostMapping("/alunosCadastro")
    public ResponseEntity<AlunosModel> saveAluno(@RequestBody @Valid AlunosDto alunosDto) {
        var alunosModel = new AlunosModel();
        BeanUtils.copyProperties(alunosDto, alunosModel);
        return ResponseEntity.status(201).body(alunosRepositorie.save(alunosModel));
    }

    @GetMapping("/todosAlunos")
    public ResponseEntity<List<AlunosModel>> getAllAlunos() {
        List<AlunosModel> alunosList = alunosRepositorie.findAll();
        if (!alunosList.isEmpty()) {
            for (AlunosModel alunos : alunosList) {
                Integer id = alunos.getIdAluno();
                alunos.add(linkTo(methodOn(AlunosController.class).getOneAluno(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(alunosList);
    }

    @GetMapping("/aluno/{id}")
    public ResponseEntity<Object> getOneAluno(@PathVariable(value = "id") Integer id) {
        Optional<AlunosModel> alunosID = alunosRepositorie.findById(id);
        if (alunosID.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado");
        }
        alunosID.get().add(linkTo(methodOn(AlunosController.class).getAllAlunos()).withRel("alunos"));
        return ResponseEntity.status(HttpStatus.OK).body(alunosID.get());
    }

    @PutMapping("/AtualizarAluno/{id}")
    public ResponseEntity<Object>updateAluno(@PathVariable(value = "id") Integer id, @RequestBody @Valid AlunosDto alunosDto) {
        Optional<AlunosModel> alunosID = alunosRepositorie.findById(id);
        if (alunosID.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado");
        }
        var alunosModel = alunosID.get();
        BeanUtils.copyProperties(alunosDto, alunosModel);
        return ResponseEntity.status(HttpStatus.OK).body(alunosRepositorie.save(alunosModel));
    }

    @DeleteMapping("/DeletarAluno/{id}")
    public ResponseEntity<Object> deleteAluno(@PathVariable(value = "id") Integer id) {
        Optional<AlunosModel> alunosID = alunosRepositorie.findById(id);
        if (alunosID.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado");
        }
        alunosRepositorie.delete(alunosID.get());
        return ResponseEntity.status(HttpStatus.OK).body("Aluno deletado com sucesso");
    }
}
