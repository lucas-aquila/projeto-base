package br.org.pti.fpti_base.domain.repository;

import java.util.concurrent.Future;

import br.org.pti.fpti_base.domain.entity.usuario.Usuario;

/**
 * 
 * Interface para o envio de e-mails
 */
public interface IUsuarioMailRepository {

	Future<Void> sendNewUserAccount(Usuario usuario, String url);

	Future<Void> sendPasswordReset(Usuario usuario, String url);

	Future<Void> sendPasswordResetNotice(Usuario usuario);
	
}