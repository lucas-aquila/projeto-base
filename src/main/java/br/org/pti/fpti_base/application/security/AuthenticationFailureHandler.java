package br.org.pti.fpti_base.application.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;

import br.org.pti.fpti_base.domain.entity.usuario.Usuario;
import br.org.pti.fpti_base.domain.repository.IUsuarioRepository;

public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler
{

	@Autowired
	IUsuarioRepository usuarioRepository;
	
	/*
     * (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException exception ) throws IOException, ServletException
	{

		if ( exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException)
		{
			String username = (String) request.getParameter("email");
			Usuario usuario = (Usuario) usuarioRepository.findByEmail(username);
			
			if (usuario != null) {
				usuario.registerFailedLogin();
				usuarioRepository.saveAndFlush(usuario);
			}
			
			
			response.setContentType( "text/plain; charset=iso-8859-1" );
			response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Nome de usuário ou senha não conferem");
			throw new BadCredentialsException("Nome de usuário ou senha não conferem");
		}

		if ( exception instanceof LockedException || exception instanceof DisabledException )
		{
			response.setContentType( "text/plain; charset=iso-8859-1" );
			response.sendError( HttpServletResponse.SC_FORBIDDEN, "Este usuário está bloqueado" );
			throw new LockedException("Usuário bloqueado devido à muitas tentativas malsucedidas");
		}

		// lança excessao caso a senha esteja expirada
		if ( exception instanceof CredentialsExpiredException )
		{
			response.setContentType( "text/plain; charset=iso-8859-1" );
			response.sendError( HttpServletResponse.SC_NOT_ACCEPTABLE, "Senha de usuário está expirada" );
			throw new CredentialsExpiredException("Nome de usuário ou senha não conferem");
		}

		/*if (exception instanceof InternalAuthenticationServiceException)
		{
			response.setContentType( "text/plain; charset=iso-8859-1" );
			response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Login não encontrado" );
			throw new InternalAuthenticationServiceException("Login não encontrado");
		}*/

		throw exception;
	}
}
