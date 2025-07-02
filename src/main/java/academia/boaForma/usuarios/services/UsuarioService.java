package academia.boaForma.usuarios.services;

import academia.boaForma.exception.CampoDuplicadoException;
import academia.boaForma.usuarios.models.UsuarioModel;
import academia.boaForma.usuarios.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioModel cadastrar(UsuarioModel usuario) {
        String senhaPura = usuario.getSenha();
        String senhaCriptografada = passwordEncoder.encode(senhaPura);
        usuario.setSenha(senhaCriptografada);
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
