package academia.boaForma.services.alunos;

import academia.boaForma.dtos.alunos.AlunosDto;
import academia.boaForma.models.alunos.informacoes.AlunosModel;
import academia.boaForma.repositories.alunos.AlunosRepositorie;
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
