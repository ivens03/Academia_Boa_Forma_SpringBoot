package academia.boaForma.gestor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GestorPrincipal {

    @GetMapping("/homeGestor")
    public String telaPrincipalGestor() {
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