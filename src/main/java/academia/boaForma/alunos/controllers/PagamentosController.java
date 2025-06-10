package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.DadosDetalhamentoPagamentosAlunos;
import academia.boaForma.alunos.dtos.DadosPagamentosAlunos;
import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
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
    private final AlunosRepositorie alunosRepositorie;

    public PagamentosController(PagamentosAlunosRepositorie pagamentosAlunosRepositorie, AlunosRepositorie alunosRepositorie) {
        this.pagamentosAlunosRepositorie = pagamentosAlunosRepositorie;
        this.alunosRepositorie = alunosRepositorie;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DadosDetalhamentoPagamentosAlunos> cadastroPagamentosAlunos(@RequestBody @Valid DadosPagamentosAlunos dadosPagamentosAlunos, UriComponentsBuilder uriBuilder) {
        // Buscar o aluno pelo nome
        var aluno = alunosRepositorie.findByNome(dadosPagamentosAlunos.nome());
        if (aluno == null) {
            throw new RuntimeException("Aluno não encontrado com o nome: " + dadosPagamentosAlunos.nome());
        }

        // Criar o pagamento
        var pagamentosAlunos = new PagamentosAlunosModel();
        pagamentosAlunos.setData_de_pagamento(dadosPagamentosAlunos.data_de_pagamento());
        pagamentosAlunos.setValor_pago(dadosPagamentosAlunos.valor_pago());
        pagamentosAlunos.setTipoPagamento(dadosPagamentosAlunos.tipoPagamento());
        pagamentosAlunos.setStatusPagamento(dadosPagamentosAlunos.statusPagamento());
        pagamentosAlunos.setStatusRecebidos(dadosPagamentosAlunos.statusRecebidos());
        pagamentosAlunos.setAluno(aluno);

        // Salvar o pagamento
        pagamentosAlunosRepositorie.save(pagamentosAlunos);

        var uri = uriBuilder.path("/pagamentosAlunos/{id_aluno}").buildAndExpand(pagamentosAlunos.getId_Pagamento()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPagamentosAlunos(pagamentosAlunos));
    }



}
