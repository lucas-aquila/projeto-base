package br.org.pti.fpti_base.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.org.pti.fpti_base.application.http.HTTPContextHolder;
import br.org.pti.fpti_base.application.i18n.MessageSourceHolder;
import br.org.pti.fpti_base.application.security.ContextHolder;
import br.org.pti.fpti_base.application.validation.StandaloneBeanValidation;
import br.org.pti.fpti_base.domain.entity.usuario.Perfil;
import br.org.pti.fpti_base.domain.entity.usuario.Usuario;
import br.org.pti.fpti_base.domain.repository.IUsuarioMailRepository;
import br.org.pti.fpti_base.domain.repository.IUsuarioRepository;

@Service
@Transactional
public class UsuarioService {

	/**
	 * 
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 
	 */
	@Autowired
    private IUsuarioRepository usuarioRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IUsuarioMailRepository usuarioMailRepository;
	
    /**
     * 
     * @param filter
     * @param status
     * @param pageable
     * @return
     */
	@Transactional(readOnly = true)
    @PreAuthorize("hasAnyAuthority('" + Perfil.ADMINISTRADOR_VALUE + "')")
    public Page<Usuario> listUsuariosByFilters( String filter, Boolean status, Pageable pageable ) {
        return this.usuarioRepository.listByFilters( filter, status, pageable );
    }

    /**
     * 
     * @param id
     * @return
     */
	@Transactional(readOnly = true)
    @PreAuthorize("hasAnyAuthority('" + Perfil.ADMINISTRADOR_VALUE + "')")
    public Usuario findUsuarioById( long id ) {
        final Usuario usuarioSaved = this.usuarioRepository.findById( id )
				.orElseThrow( () -> new IllegalArgumentException( MessageSourceHolder.getMessage("repository.notFoundById", id ) ) );
        
        return usuarioSaved;
    }
    
    /**
     * 
     * @param usuario
     * @return
     */
    @PreAuthorize("hasAnyAuthority('" + Perfil.ADMINISTRADOR_VALUE + "')")
    public Usuario insertUsuario( Usuario usuario ) {
    	
    	usuario.setStatus( true );
    	
    	String codigo = this.gerarCodigo();
    	usuario.setCodigo( codigo );    	
    	usuario.setSenha( this.passwordEncoder.encode( codigo ) );
    	
    	/*if ( usuario.getOrganizacao() != null && usuario.getOrganizacao().getId() != null ) {    		
	    	Organizacao organizacaoSaved = this.organizacaoRepository.save( usuario.getOrganizacao() );
	    	usuario.setOrganizacao( organizacaoSaved );
    	} else {
    		usuario.setOrganizacao( null );
    	}*/
    	
    	final Usuario usuarioSaved = this.usuarioRepository.save( usuario );
    	
		final String url = HTTPContextHolder.getServerURL()+"/#/cadastrar-senha/"+codigo;
    	
    	usuarioMailRepository.sendNewUserAccount(usuario, url);
    	
    	return usuarioSaved;
    }
    
    /**
     * 
     * @param usuario
     * @return
     */
    @PreAuthorize("hasAnyAuthority('" + Perfil.ADMINISTRADOR_VALUE + "')")
    public Usuario updateUsuario( Usuario usuario ) {
    	
        Usuario authenticatedUser = ContextHolder.getAuthenticatedUser();
        
        Usuario usuarioSaved = this.usuarioRepository.findById( usuario.getId() )
				.orElseThrow( () -> new IllegalArgumentException( MessageSourceHolder.getMessage("repository.notFoundById", usuario.getId() ) ) );
        
        usuario.setSenha( usuarioSaved.getSenha() );
        
        if ( authenticatedUser.getId().equals( usuarioSaved.getId() ) ) {
            usuario.setPerfis( usuarioSaved.getPerfis() );
        }
        
        return this.usuarioRepository.saveAndFlush( usuario );
    }
    
	/**
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('"+ Perfil.ADMINISTRADOR_VALUE +"')")
	public Usuario updateStatusUsuario( long id )
	{	
		Usuario authenticatedUser = ContextHolder.getAuthenticatedUser();    	
		Usuario usuarioSaved = this.usuarioRepository.findById( id )
				.orElseThrow( () -> new IllegalArgumentException( MessageSourceHolder.getMessage("repository.notFoundById", id ) ) );
		
		Assert.notNull( usuarioSaved, MessageSourceHolder.getMessage("repository.notFoundById", id) );
		Assert.isTrue( !authenticatedUser.getId().equals( usuarioSaved.getId() ), MessageSourceHolder.getMessage("security.accessDenied"));
    	
		usuarioSaved.setStatus( !usuarioSaved.getStatus() );
		
		return this.usuarioRepository.saveAndFlush( usuarioSaved );
	}

    /**
     * 
     * @param id
     * @param senhaAtual
     * @param novaSenha
     * @return
     */
	public void updateSenhaUsuario( Long id, String senhaAtual, String novaSenha ) {
		
		final Usuario usuarioAutenticado = ContextHolder.getAuthenticatedUser();
		
		Assert.isTrue( usuarioAutenticado.getId().equals( id ), MessageSourceHolder.getMessage("security.accessDenied") );
		final Usuario usuario = this.usuarioRepository.findById( id )
				.orElseThrow( () -> new IllegalArgumentException( MessageSourceHolder.getMessage("repository.notFoundById", id ) ) );
		
		Assert.notNull( senhaAtual, "A senha atual não pode ser vazia." );
		Assert.notNull( novaSenha, "A nova senha não pode ser vazia." );
		
		Assert.isTrue( BCrypt.checkpw(senhaAtual, usuario.getSenha()), "A senha atual está incorreta." );
		
		//somente para fins de validação, sem econdar a senha
		usuario.setSenha( novaSenha );
		new StandaloneBeanValidation().validateAndThrow(usuario);
		
		usuario.setSenha( this.passwordEncoder.encode( novaSenha ) );
		
		this.usuarioRepository.saveAndFlush( usuario );
	}
    
	/**
	 * Gera e-mail de recuperação de senha
	 * @param email
	 */
	public void recoverSenha( String email ) {
		Assert.notNull( email, "O email deve ser preenchido" );
		Usuario usuario = (Usuario) this.usuarioRepository.findByEmail( email );

		if ( usuario == null ) {
			//por razões de segurança nenhuma mensagem será retornada caso o email não exista
			return;
			//throw new UsernameNotFoundException( MessageSourceHolder.getMessage( "authentication.emailNotFound", null, LocaleContextHolder.getLocale() ) );
		}

		if ( !usuario.getStatus() ) {
			throw new DisabledException( "Este usuário está desativado" );//MessageSourceHolder.getMessage( "authentication.disabledUser", null, LocaleContextHolder.getLocale() ) );
		}


		final String codigo = this.gerarCodigo();
		final String url = HTTPContextHolder.getServerURL()+"/#/cadastrar-senha/"+codigo;
		
		usuario.setCodigo( codigo );
		this.usuarioRepository.saveAndFlush( usuario );
		
		this.usuarioMailRepository.sendPasswordReset( usuario, url );
	}

	/**
	 * Atualiza a senha de um Usuário através da função "Esqueci Minha Senha" com o token
	 * @param codigo
	 */
	public void resetSenha( String codigo, String senha )
	{
		Assert.notNull( codigo, "O código de segurança deve ser informado" );
		Usuario usuario = this.usuarioRepository.findByCodigo( codigo );

		
		//somente para fins de validação, sem encodar a senha
		usuario.setSenha( senha );
		new StandaloneBeanValidation().validateAndThrow(usuario);
		
		usuario.setSenha( this.passwordEncoder.encode( senha ) );
		usuario.setCodigo( null );

		this.usuarioRepository.saveAndFlush( usuario );
	}

	/**
	 * 
	 * @return código (token) aleatório de segurança com 32 caracteres
	 */
    private String gerarCodigo() {
    	return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 
     * @param filter
     * @param status
     * @param pageable
     * @return
     */
	@Transactional(readOnly = true)    
    public List<Usuario> listUsuariosByNomeAndPerfil( String nome, Perfil perfil ) {
        return this.usuarioRepository.listByNomeAndPerfil( nome, perfil );
    }
}
