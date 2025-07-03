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

        // Busca o aluno pelo ID usando o método alunoById do repositório
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
}
