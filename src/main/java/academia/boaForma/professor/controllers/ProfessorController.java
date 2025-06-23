package academia.boaForma.professor.controllers;

import academia.boaForma.professor.dtos.DadosAtualizarProfessor;
import academia.boaForma.professor.dtos.DadosCadastroProfessor;
import academia.boaForma.professor.dtos.DadosDetalhamentoProfessor;
import academia.boaForma.professor.dtos.DadosListarProfessores;
import academia.boaForma.professor.models.Professor;
import academia.boaForma.professor.repositories.ProfessorRepositorie;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity<Page<DadosListarProfessores>> listarProfessor(Pageable paginacao) {
        var listagemProfessoresAtivos = professorRepositorie.findAllAcessoSistema(paginacao).map(DadosListarProfessores::new);
        return ResponseEntity.ok(listagemProfessoresAtivos);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DadosListarProfessores>> listarTodosProfessores(Pageable paginacao) {
        var listagemTodosProfessores = professorRepositorie.findAll(paginacao).map(DadosListarProfessores::new);
        return ResponseEntity.ok(listagemTodosProfessores);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarProfessor(@RequestBody @Valid DadosAtualizarProfessor dadosAtualizarProfessor) {
        var professor = professorRepositorie.professorById(dadosAtualizarProfessor.id());
        professor.atualizarInformacoes(dadosAtualizarProfessor);
        professorRepositorie.save(professor);
        return ResponseEntity.ok("Professor atualizado com sucesso!");
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarProfessor(@PathVariable Integer id) {
        var professor = professorRepositorie.professorById(id);
        professor.usuarioDesativado();
        professorRepositorie.save(professor);
        return ResponseEntity.noContent().build();
    }

}
