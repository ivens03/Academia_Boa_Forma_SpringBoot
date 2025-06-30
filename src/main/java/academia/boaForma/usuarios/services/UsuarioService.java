package academia.boaForma.usuarios.services;

import academia.boaForma.usuarios.models.UsuarioModel;
import academia.boaForma.usuarios.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioModel cadastrar(UsuarioModel usuario) {
        String senhaPura = usuario.getSenha();
        String senhaCriptografada = passwordEncoder.encode(senhaPura);
        usuario.setSenha(senhaCriptografada);
        return usuarioRepository.save(usuario);
    }
}
