package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.pagamentosDtos.DadosCadastroPagamento;
import academia.boaForma.alunos.dtos.pagamentosDtos.DadosDetalhamentoPagamento;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import academia.boaForma.alunos.repositories.PagamentosAlunosRepositorie;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pagamentosAlunos")
public class PagamentosController {

    private final PagamentosAlunosRepositorie pagamentosAlunosRepositorie;
    private final AlunosRepositorie alunosRepositorie;

    @Autowired
    public PagamentosController(PagamentosAlunosRepositorie pagamentosAlunosRepositorie, AlunosRepositorie alunosRepositorie) {
        this.pagamentosAlunosRepositorie = pagamentosAlunosRepositorie;
        this.alunosRepositorie = alunosRepositorie;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DadosDetalhamentoPagamento> cadastroPagamento(
            @RequestBody @Valid DadosCadastroPagamento dadosCadastroPagamento,
            UriComponentsBuilder uriBuilder) {
        AlunosModel aluno = alunosRepositorie.alunoById(dadosCadastroPagamento.id_aluno().intValue());
        if (aluno == null) {
            throw new EntityNotFoundException("Aluno não encontrado com o ID: " + dadosCadastroPagamento.id_aluno());
        }
        // Cria o pagamento associando o aluno
        var pagamento = new PagamentosAlunosModel(dadosCadastroPagamento, aluno);
        pagamentosAlunosRepositorie.save(pagamento);
        var uri = uriBuilder.path("/pagamentosAlunos/{id}")
                .buildAndExpand(pagamento.getId_Pagamento())
                .toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPagamento(pagamento));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPagamento> atualizarPagamento(
            @RequestBody @Valid DadosCadastroPagamento dadosCadastroPagamento) {
        AlunosModel aluno = alunosRepositorie.alunoById(dadosCadastroPagamento.id_aluno().intValue());
        if (aluno == null) {
            throw new EntityNotFoundException("Aluno nao encontrado com o ID: " + dadosCadastroPagamento.id_aluno());
        }
        var mudancaPagamento = new PagamentosAlunosModel(dadosCadastroPagamento, aluno);
        pagamentosAlunosRepositorie.save(mudancaPagamento);
        return ResponseEntity.ok(new DadosDetalhamentoPagamento(mudancaPagamento));
    }

    @GetMapping("/{id}")
    public Page<DadosDetalhamentoPagamento> dadosDePagamento(Pageable paginacao){
        var listagemPagamentos = pagamentosAlunosRepositorie.findAll(paginacao).map(DadosDetalhamentoPagamento::new);
        return ResponseEntity.ok(listagemPagamentos).getBody();
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<Object> deletarPagamento(@PathVariable(value = "id") Integer id) {
        PagamentosAlunosModel apagarIdPagamento = pagamentosAlunosRepositorie.pagamentosAlunosById(id);
        if (apagarIdPagamento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado");
        }
        pagamentosAlunosRepositorie.delete(apagarIdPagamento);
        return ResponseEntity.status(HttpStatus.OK).body("Pagamento deletado com sucesso");
    }
}
