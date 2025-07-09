package academia.boaForma.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um usuário tenta acessar o sistema mas não tem permissão.
 * Ocorre quando o campo 'acessoSistema' do usuário for igual a false.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class SemAcessoSistema extends RuntimeException {

    /**
     * Cria uma nova instância da exceção com o nome do usuário.
     * @param nomeUsuario Nome do usuário que tentou acessar sem permissão
     */
    public SemAcessoSistema(String nomeUsuario) {
        super(String.format(nomeUsuario + "Acesso negado para o usuário '%s'. Para normalizar o acesso, entre em contato com o administrador do sistema." ));
    }
}
