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
}
