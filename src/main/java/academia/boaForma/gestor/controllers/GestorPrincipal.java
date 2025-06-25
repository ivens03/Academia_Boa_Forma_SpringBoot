package academia.boaForma.gestor.controllers;

import academia.boaForma.alunos.controllers.AlunosController;
import academia.boaForma.alunos.dtos.DadosListarAlunos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GestorPrincipal {
    
    private final AlunosController alunosController;
    
    public GestorPrincipal(AlunosController alunosController) {
        this.alunosController = alunosController;
    }

    @GetMapping("/homeGestor")
    public String telaPrincipalGestor(@PageableDefault(size = 10) Pageable paginacao, Model model) {
        Page<DadosListarAlunos> alunos = alunosController.listarAlunos(paginacao);
        model.addAttribute("alunos", alunos.getContent());
        return "gestor";
    }

    @GetMapping("/cadastroAluno")
    public String telaCadastroAluno() {
        return "registroAluno";
    }

    @GetMapping("/cadastroProfessor")
    public String telaCadastroProfessor() {
        return "registroProfessor";
    }

    @GetMapping("/gerenciamentoAlunos")
    public String telaGerenciamentoAlunos() {
        return "gerenciamentoAlunos";
    }

    @GetMapping("/login")
    public String telaLogin() {
        return "login";
    }
}
