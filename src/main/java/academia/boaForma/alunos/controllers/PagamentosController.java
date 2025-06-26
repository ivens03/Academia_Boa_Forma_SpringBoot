package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.dtos.DadosDetalhamentoPagamentosAlunos;
import academia.boaForma.alunos.dtos.DadosPagamentosAlunos;
import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import academia.boaForma.alunos.models.pagamentos.StatusPagamentoEnum;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import academia.boaForma.alunos.repositories.PagamentosAlunosRepositorie;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pagamentosAlunos")
public class PagamentosController {

    private final PagamentosAlunosRepositorie pagamentosAlunosRepositorie;
    private final AlunosRepositorie alunosRepositorie;

    public PagamentosController(PagamentosAlunosRepositorie pagamentosAlunosRepositorie, AlunosRepositorie alunosRepositorie) {
        this.pagamentosAlunosRepositorie = pagamentosAlunosRepositorie;
        this.alunosRepositorie = alunosRepositorie;
    }

    @GetMapping("/statusMensalidade")
    public ResponseEntity<List<String>> listarFocoAluno() {
        return ResponseEntity.ok(Arrays.stream(StatusPagamentoEnum.values())
                .map(StatusPagamentoEnum::name)
                .collect(Collectors.toList()));
    }
}
