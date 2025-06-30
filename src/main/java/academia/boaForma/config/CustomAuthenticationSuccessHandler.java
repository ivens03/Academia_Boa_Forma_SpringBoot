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

        String redirectUrl = null;

        label:
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            switch (role) {
                case "ROLE_ALUNO":
                    redirectUrl = "/alunoHome";
                    break label; // Encontrou a role, pode parar o loop
                case "ROLE_PROFESSOR":
                    redirectUrl = "/professorHome";
                    break label;
                case "ROLE_GESTOR":
                    redirectUrl = "/homeGestor";
                    break label;
            }
        }

        if (redirectUrl == null) {
            throw new IllegalStateException("Nenhuma role encontrada para o usuário ou nenhuma regra de redirecionamento definida.");
        }
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}
