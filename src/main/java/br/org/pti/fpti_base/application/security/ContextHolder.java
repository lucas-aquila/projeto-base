package br.org.pti.fpti_base.application.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.org.pti.fpti_base.domain.entity.usuario.Usuario;

public class ContextHolder {
	
    /**
     * 
     * @return
     */
    public static Usuario getAuthenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Usuario) {
            return (Usuario) authentication.getPrincipal();
        }

        throw new AuthenticationCredentialsNotFoundException("O usuário não está autenticado");
    }

    /**
     * 
     * @return
     */
    public static boolean isAuthenticated() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null && authentication.getPrincipal() != null && !authentication.getPrincipal().equals("anonymousUser");
    }

}
