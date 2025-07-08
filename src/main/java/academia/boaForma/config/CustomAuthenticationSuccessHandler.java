package academia.boaForma.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String redirectUrl = determineTargetUrl(authentication);

        if (redirectUrl != null) {
            response.sendRedirect(request.getContextPath() + redirectUrl);
        } else {
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
                return "/homeAluno"; // Ponto e vírgula adicionado.
            }
            if ("ROLE_PROFESSOR".equals(role)) {
                return "/homeProfessor"; // Ponto e vírgula adicionado.
            }
        }

        return null;
    }
}