/*
package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.PagamentosAlunosDto;
import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import academia.boaForma.alunos.repositories.PagamentosAlunosRepositorie;
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
public class PagamentosAlunosController {

    @Autowired
    PagamentosAlunosRepositorie pagamentosAlunosRepositorie;

    @PostMapping("/RegistroPagamentos")
    public ResponseEntity<PagamentosAlunosModel> savePagamentosAlunos(@RequestBody @Valid PagamentosAlunosDto pagamentosAlunosDto) {
        var pagamentosAlunosModel = new PagamentosAlunosModel();
        BeanUtils.copyProperties(pagamentosAlunosDto, pagamentosAlunosModel);
        return ResponseEntity.status(201).body(pagamentosAlunosRepositorie.save(pagamentosAlunosModel));
    }

    @GetMapping("/TodosRegistrosPagamentos")
    public ResponseEntity<List<PagamentosAlunosModel>> getAllPagamentosAlunos() {
        List<PagamentosAlunosModel> pagamentosAlunosList = pagamentosAlunosRepositorie.findAll();
        if (!pagamentosAlunosList.isEmpty()) {
            for (PagamentosAlunosModel pagamentosAlunos : pagamentosAlunosList) {
                Integer id = pagamentosAlunos.getIdPagamento();
                pagamentosAlunos.add(linkTo(methodOn(PagamentosAlunosController.class).getOnePagamentosAlunos(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(pagamentosAlunosList);
    }

    @GetMapping("/PagamentosAlunos/{id}")
    public ResponseEntity<Object> getOnePagamentosAlunos(@PathVariable(value = "id") Integer id) {
        Optional<PagamentosAlunosModel> pagamentosAlunosID = pagamentosAlunosRepositorie.findById(id);
        if (pagamentosAlunosID.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado");
        }
        pagamentosAlunosID.get().add(linkTo(methodOn(PagamentosAlunosController.class).getAllPagamentosAlunos()).withRel("pagamentosAlunos"));
        return ResponseEntity.status(HttpStatus.OK).body(pagamentosAlunosID.get());
    }

    @PutMapping("/AtualizarPagamentosAlunos/{id}")
    public ResponseEntity<Object> updatePagamentosAlunos(@PathVariable(value = "id") Integer id, @RequestBody @Valid PagamentosAlunosDto pagamentosAlunosDto) {
        Optional<PagamentosAlunosModel> pagamentosAlunosID = pagamentosAlunosRepositorie.findById(id);
        if (pagamentosAlunosID.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado");
        }
        var pagamentosAlunosModel = pagamentosAlunosID.get();
        BeanUtils.copyProperties(pagamentosAlunosDto, pagamentosAlunosModel);
        return ResponseEntity.status(HttpStatus.OK).body(pagamentosAlunosRepositorie.save(pagamentosAlunosModel));
    }

    @DeleteMapping("/DeletarPagamentosAlunos/{id}")
    public ResponseEntity<Object> deletePagamentosAlunos(@PathVariable(value = "id") Integer id) {
        Optional<PagamentosAlunosModel> pagamentosAlunosID = pagamentosAlunosRepositorie.findById(id);
        if (pagamentosAlunosID.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado");
        }
        pagamentosAlunosRepositorie.delete(pagamentosAlunosID.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pagamento deletado com sucesso");
    }
}
*/
