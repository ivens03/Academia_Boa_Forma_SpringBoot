package academia.boaForma.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    // ✅ Este é o método principal que o Spring executa.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 1. Chamar o método auxiliar para descobrir a URL de destino.
        String redirectUrl = determineTargetUrl(authentication);

        // 2. Se uma URL foi encontrada, realizar o redirecionamento.
        if (redirectUrl != null) {
            response.sendRedirect(request.getContextPath() + redirectUrl);
        } else {
            // Opcional: Lançar um erro se nenhuma permissão válida for encontrada.
            throw new IllegalStateException("Não foi possível determinar a URL de redirecionamento para o usuário.");
        }
    }

    // ✅ Este é o método auxiliar, agora no lugar correto (fora do outro método).
    protected String determineTargetUrl(Authentication authentication) {
        // O laço for é essencial para verificar CADA permissão na lista.
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();

            // Usamos o if para checar a permissão da rodada atual.
            if ("ROLE_GESTOR".equals(role)) {
                return "/homeGestor"; // Ponto e vírgula adicionado.
            }
            if ("ROLE_ALUNO".equals(role)) {
                return "/alunoHome"; // Ponto e vírgula adicionado.
            }
            if ("ROLE_PROFESSOR".equals(role)) {
                return "/professorHome"; // Ponto e vírgula adicionado.
            }
        }

        // Se o laço terminar e nenhuma permissão for encontrada, retorna nulo.
        return null;
    }
}