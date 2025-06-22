package academia.boaForma.professor.controllers;

import academia.boaForma.professor.dtos.DadosCadastroProfessor;
import academia.boaForma.professor.dtos.DadosDetalhamentoProfessor;
import academia.boaForma.professor.models.Professor;
import academia.boaForma.professor.repositories.ProfessorRepositorie;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorRepositorie professorRepositorie;

    public ProfessorController(ProfessorRepositorie professorRepositorie) { this.professorRepositorie = professorRepositorie; }

    @Transactional
    @PostMapping
    public ResponseEntity<DadosDetalhamentoProfessor> cadastroProfessor(@RequestBody @Valid DadosCadastroProfessor dadosCadastroProfessor, UriComponentsBuilder uriBuilder) {
        var professor = new Professor(dadosCadastroProfessor);
        professorRepositorie.save(professor);
        var uri = uriBuilder.path("/professor/{id}").buildAndExpand(professor.getId()).toUri();
        System.out.println(dadosCadastroProfessor);
        return ResponseEntity.created(uri).body(new DadosDetalhamentoProfessor(professor));
    }

}
