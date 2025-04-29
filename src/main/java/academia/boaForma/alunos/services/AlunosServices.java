package academia.boaForma.alunos.services;

import academia.boaForma.alunos.dtos.AlunosDto;
import academia.boaForma.alunos.models.informacoes.AlunosModel;
import academia.boaForma.alunos.repositories.AlunosRepositorie;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AlunosServices {

    private final AlunosRepositorie alunosRepositorie;

    public AlunosServices(AlunosRepositorie alunosRepositorie) {
        this.alunosRepositorie = alunosRepositorie;
    }

    public AlunosModel salvarAluno(AlunosDto alunosDto) {
        var aluno = new AlunosModel();
        BeanUtils.copyProperties(alunosDto, aluno);
        return alunosRepositorie.save(aluno);
    }
}
