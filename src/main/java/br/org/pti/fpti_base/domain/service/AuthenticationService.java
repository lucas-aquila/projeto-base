package br.org.pti.fpti_base.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.org.pti.fpti_base.application.security.ContextHolder;
import br.org.pti.fpti_base.domain.entity.usuario.Usuario;
import br.org.pti.fpti_base.domain.repository.IUsuarioRepository;

@Service
@Transactional
public class AuthenticationService implements UserDetailsService {

	/**
	 * 
	 */
	@Autowired
    private IUsuarioRepository usuarioRepository;

	/**
	 * 
	 */
    @Override
    public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {
        return this.usuarioRepository.findByEmail( email );
    }

    /**
     * Retorna o usuário autenticado
     *
     * @return
     */
    public Usuario getAuthenticatedUser() {
        Assert.isTrue(ContextHolder.isAuthenticated(), "Não autenticado"); //TODO o correto seria throw new AccessDeniedException("Não autenticado");
        return this.usuarioRepository.findById(ContextHolder.getAuthenticatedUser().getId()).get();
    }
}
