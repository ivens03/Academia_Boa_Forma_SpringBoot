package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.DadosDetalhamentoPagamentosAlunos;
import academia.boaForma.alunos.dtos.DadosPagamentosAlunos;
import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import academia.boaForma.alunos.repositories.PagamentosAlunosRepositorie;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pagamentosAlunos")
public class PagamentosController {

    private final PagamentosAlunosRepositorie pagamentosAlunosRepositorie;

    public PagamentosController(PagamentosAlunosRepositorie pagamentosAlunosRepositorie) {
        this.pagamentosAlunosRepositorie = pagamentosAlunosRepositorie;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DadosDetalhamentoPagamentosAlunos> cadastroPagamentosAlunos(@RequestBody @Valid DadosPagamentosAlunos dadosPagamentosAlunos, UriComponentsBuilder uriBuilder) {
        var pagamentosAlunos = new PagamentosAlunosModel(dadosPagamentosAlunos);
        pagamentosAlunosRepositorie.save(pagamentosAlunos);
        var uri = uriBuilder.path("/pagamentosAlunos/{id_aluno}").buildAndExpand(pagamentosAlunos.getId_Pagamento()).toUri();
        System.out.println(dadosPagamentosAlunos);
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPagamentosAlunos(pagamentosAlunos));
    }



}
