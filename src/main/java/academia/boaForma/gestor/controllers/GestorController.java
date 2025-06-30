package academia.boaForma.gestor.controllers;

import academia.boaForma.alunos.dtos.pagamentosDtos.DadosCadastroPagamento;
import academia.boaForma.gestor.dtos.DadosCadastroGestor;
import academia.boaForma.gestor.dtos.DadosDetalhamentoGestor;
import academia.boaForma.gestor.models.GestorModel;
import academia.boaForma.gestor.repositories.GestorRepositorie;
import academia.boaForma.usuarios.services.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/gestor")
public class GestorController {

    private final GestorRepositorie gestorRepositorie;
    private final UsuarioService usuarioService;

    public GestorController(GestorRepositorie gestorRepositorie,UsuarioService usuarioService) {
        this.gestorRepositorie = gestorRepositorie;
        this.usuarioService = usuarioService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DadosDetalhamentoGestor> cadastroGestor (@RequestBody @Valid DadosCadastroGestor dadosCadastroGestor, UriComponentsBuilder uriBuilder) {
        var gestor = new GestorModel(dadosCadastroGestor);
        var gestorSalvo = usuarioService.cadastrar(gestor);
        var uri = uriBuilder.path("/gestor/{id}").buildAndExpand(gestorSalvo.getId()).toUri();
        System.out.println(dadosCadastroGestor);
        return ResponseEntity.created(uri).body(new DadosDetalhamentoGestor((GestorModel) gestorSalvo));
    }

}
