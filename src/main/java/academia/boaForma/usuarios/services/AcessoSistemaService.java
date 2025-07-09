package academia.boaForma.usuarios.services;

import academia.boaForma.exception.SemAcessoSistema;
import academia.boaForma.usuarios.models.UsuarioModel;
import academia.boaForma.usuarios.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por gerenciar as regras de acesso dos usuários ao sistema.
 * Verifica se um usuário tem permissão para acessar o sistema com base no seu status de ativação.
 */
@Service
public class AcessoSistemaService {

    private final UsuarioRepository usuarioRepository;

    AcessoSistemaService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Verifica se o usuário tem permissão para acessar o sistema.
     * @param email Email do usuário
     * @throws SemAcessoSistema Se o usuário não tiver permissão de acesso
     */
    public void verificarAcessoSistema(String email) {
        UsuarioModel usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new SemAcessoSistema("Usuário não encontrado"));

        if (Boolean.FALSE.equals(usuario.getAcessoSistema())) {
            throw new SemAcessoSistema(usuario.getNome());
        }
    }

}
