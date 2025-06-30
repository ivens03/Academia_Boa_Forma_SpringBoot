package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.pagamentosDtos.DadosCadastroPagamento;
import academia.boaForma.alunos.dtos.pagamentosDtos.DadosDetalhamentoPagamento;
import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import academia.boaForma.alunos.repositories.PagamentosAlunosRepositorie;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pagamentosAlunos")
public class PagamentosController {

    private final PagamentosAlunosRepositorie pagamentosAlunosRepositorie;

    public PagamentosController(PagamentosAlunosRepositorie pagamentosAlunosRepositorie) { this.pagamentosAlunosRepositorie = pagamentosAlunosRepositorie; }

    @Transactional
    @PostMapping
    public ResponseEntity<DadosDetalhamentoPagamento> cadastroPagamento(@RequestBody@Valid DadosCadastroPagamento dadosCadastroPagamento, UriComponentsBuilder uriBuilder) {
        var pagamento = new PagamentosAlunosModel(dadosCadastroPagamento);
        pagamentosAlunosRepositorie.save(pagamento);
        var uri = uriBuilder.path("/pagamentosAlunos/{id}").buildAndExpand(pagamento.getId_Pagamento()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPagamento(pagamento));
    }


}
