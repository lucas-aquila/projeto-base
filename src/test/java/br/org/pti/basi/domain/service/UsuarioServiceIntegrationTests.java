package br.org.pti.basi.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;

import br.org.pti.basi.domain.AbstractIntegrationTests;
import br.org.pti.fpti_base.domain.entity.usuario.Usuario;
import br.org.pti.fpti_base.domain.service.UsuarioService;

@Sql({
	"/dataset/usuarios.sql",
	"/dataset/usuario_perfil.sql"
})
@WithUserDetails("admin@admin.com")
public class UsuarioServiceIntegrationTests extends AbstractIntegrationTests {
	
	/**
	 * 
	 */
	@Autowired
	private UsuarioService usuarioService;

    /**
     *
     */
	@Test
    public void findUsuarioByIdMustPass() {
    	final Usuario usuario = this.usuarioService.findUsuarioById( 1001L );
    	
    	assertNotNull( usuario );
    	assertNotNull( usuario.getId() );
    	assertEquals("admin@admin.com", usuario.getEmail());
    }
    
	/**
	 * 
	 */
    @Test(expected = IllegalArgumentException.class)
    public void findUsuarioByIdMustFail() {
    	this.usuarioService.findUsuarioById( 9999L ); 	
    }
    
	/**
	 * 
	 */
	@Test
    public void listUysuariosByFiltersMustPass() {
    	final Page<Usuario> usuarios = this.usuarioService.listUsuariosByFilters(null, null, null);
    	
    	assertTrue( !usuarios.getContent().isEmpty() );
    	assertTrue( usuarios.getContent().stream().anyMatch(u -> u.getNome().equalsIgnoreCase("administrador de sistemas")));
    }
}
