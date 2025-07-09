package academia.boaForma.usuarios.services;

import academia.boaForma.exception.CampoDuplicadoException;
import academia.boaForma.usuarios.models.UsuarioModel;
import academia.boaForma.usuarios.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pela criptografia da senha.
 * Definição de senha padrão.
 */

@Service
public class UsuarioService {
    
    // Senha padrão para novos usuários
    private static final String SENHA_PADRAO = "BoaForma2025";
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UsuarioModel cadastrar(UsuarioModel usuario) {

        // Define a senha padrão e criptografa
        String senhaCriptografada = passwordEncoder.encode(SENHA_PADRAO);
        usuario.setSenha(senhaCriptografada);
        
        // Valida campos únicos
        List<String> camposDuplicados = new ArrayList<>();
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            camposDuplicados.add("email");
        }
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            camposDuplicados.add("cpf");
        }
        if (usuarioRepository.existsByTelefone(usuario.getTelefone())) {
            camposDuplicados.add("telefone");
        }
        if (usuarioRepository.existsByNome(usuario.getNome())) {
            camposDuplicados.add("nome");
        }
        if (!camposDuplicados.isEmpty()) {
            throw new CampoDuplicadoException(camposDuplicados);
        }
        return usuarioRepository.save(usuario);
    }

}
