package br.org.pti.fpti_base.domain.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.pti.fpti_base.domain.entity.usuario.Usuario;
import br.org.pti.fpti_base.domain.service.AuthenticationService;
import br.org.pti.fpti_base.domain.service.UsuarioService;

@RestController
public class AuthenticationController {
	
	/**
	 * 
	 */
	@Autowired
    private AuthenticationService authenticationService;
	
	/**
	 * 
	 */
	@Autowired
	private UsuarioService usuarioService; 

	/**
	 * 
	 * @return
	 */
	@GetMapping("authenticated")
    public Usuario getAuthenticated() {
        return this.authenticationService.getAuthenticatedUser();
    }
    
    /**
     * 
     * @return
     */
    @PostMapping("/cadastrar-senha/{codigo}")
    public void resetSenha( @PathVariable String codigo, HttpServletRequest request ) {
    	final String senha = request.getParameter("senha");
    	this.usuarioService.resetSenha( codigo, senha );
    }
    
	
	/**
     * 
     * @return
     */
	@GetMapping("/recuperar-senha/{email:.+}")
	public void recoverPassword(@PathVariable("email") String email) {
		//this.usuarioService.resetSenhaUsuario(email);
		this.usuarioService.recoverSenha(email);
	}
}
