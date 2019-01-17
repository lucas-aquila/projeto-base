package br.org.pti.fpti_base.domain.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.pti.fpti_base.domain.entity.usuario.Perfil;
import br.org.pti.fpti_base.domain.entity.usuario.Usuario;
import br.org.pti.fpti_base.domain.service.UsuarioService;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioController {

	/**
	 * 
	 */
	@Autowired
    private UsuarioService usuarioService;

	/**
	 * 
	 * @param filter
	 * @param status
	 * @param pageable
	 * @return
	 */
    @GetMapping
    public Page<Usuario> listUsuariosByFilters( String filter, Boolean status, Pageable pageable ) {
        return this.usuarioService.listUsuariosByFilters( filter, status, pageable );
    }

    /**
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Usuario findUsuarioById( @PathVariable long id ) {
        return this.usuarioService.findUsuarioById( id );
    }

    /**
     * 
     * @param usuario
     * @return
     */
    @PostMapping
    public Usuario insertUsuario( @RequestBody Usuario usuario ) {
    	return this.usuarioService.insertUsuario( usuario );
    }

    /**
     * 
     * @param id
     * @param usuario
     * @return
     */
    @PutMapping("/{id}")
    public Usuario updateUsuario( @PathVariable long id, @RequestBody Usuario usuario ) {
        return this.usuarioService.updateUsuario( usuario );
    }

    /**
     * 
     * @param id
     * @return
     */
	@PutMapping("/status/{id}")
	public Usuario updateStatusUsuario( @PathVariable("id") long id ) {
		return this.usuarioService.updateStatusUsuario( id );
	}
            
    /**
     * 
     * @param id
     * @param senhaAtual
     * @param novaSenha
     * @return
     */
    @PutMapping("/alterar-senha/{id}")
    public void updateSenhaUsuario( @PathVariable Long id, HttpServletRequest request ) {
    	String senhaAtual = request.getParameter("senhaAtual");
		String novaSenha = request.getParameter("novaSenha");
		
    	this.usuarioService.updateSenhaUsuario( id, senhaAtual, novaSenha );
    }
    
    /**
     * 
     * @return
     */
	@GetMapping("/enum/perfis")
	public Perfil[] listEnumPerfis() {
		return Perfil.values();
	}
	
	/**
     * 
     * @return
     */
	@GetMapping("/perfil")    
    public List<Usuario> listUsuariosByNomeAndPerfil( String nome, Perfil perfil ) {
        return this.usuarioService.listUsuariosByNomeAndPerfil( nome, perfil );
    }
}
