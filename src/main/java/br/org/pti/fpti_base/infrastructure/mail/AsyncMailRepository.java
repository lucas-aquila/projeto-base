package br.org.pti.fpti_base.infrastructure.mail;

import java.util.concurrent.Future;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.org.pti.fpti_base.domain.entity.usuario.Usuario;
import br.org.pti.fpti_base.domain.repository.IUsuarioMailRepository;

@Component
public class AsyncMailRepository implements IUsuarioMailRepository {
	
	/**
     *
     */
	@Autowired
	private JavaMailSender mailSender;
	/**
     *
     */
	@Autowired
	private TemplateEngine templateEngine;
	/**
     *
     */
	@Value("${spring.mail.from}")
	private String mailFrom;

	/**
	 *
	 * @param usuario
	 */
	@Async
	@Override
	public Future<Void> sendNewUserAccount( final Usuario usuario, String url )
	{
		final MimeMessagePreparator preparator = new MimeMessagePreparator()
		{
			public void prepare( MimeMessage mimeMessage ) throws Exception
			{
				final String title = "Bem vindo ao BASI";
				final String logo = "logo-pti.png"; // alt
				
				final MimeMessageHelper message = new MimeMessageHelper( mimeMessage, true, "UTF-8" );
				message.setSubject( title );
				message.setTo( usuario.getEmail() );
				message.setFrom( mailFrom );

				final Context context = new Context();
				context.setVariable("title", title);
				context.setVariable("logo", logo);
				context.setVariable("user", usuario);
				context.setVariable("url", url);
				
				final String content = templateEngine.process( "mail-templates/new-account", context );
				message.setText( content, true );

				//Add the inline image, referenced from the HTML code as "cid:${logo}"

				message.addInline( logo, new ClassPathResource("META-INF/resources/static/images/logo-pti.png"));
			}
		};

		this.mailSender.send( preparator );
		return new AsyncResult<>( null );
//		return null;
	}
	
	@Async
	@Override
	public Future<Void> sendPasswordReset( final Usuario usuario, final String url)
	{
		final MimeMessagePreparator preparator = new MimeMessagePreparator()
		{
			public void prepare( MimeMessage mimeMessage ) throws Exception
			{
				final String title = "BASI II - Recuperação de senha";
				final String logo = "logo-pti.png";
				
				final MimeMessageHelper message = new MimeMessageHelper( mimeMessage, true, "UTF-8" );
				message.setSubject( title );
				message.setTo( usuario.getEmail() );
				message.setFrom( mailFrom );
				
				final Context context = new Context();
				context.setVariable("title", title);
				context.setVariable("logo", logo);
				context.setVariable("url", url);
				context.setVariable("user", usuario);
				
				
				final String content = templateEngine.process( "mail-templates/reset-password", context );
				message.setText( content, true );
				
				//Add the inline image, referenced from the HTML code as "cid:${logo}"
				message.addInline( logo, new ClassPathResource("META-INF/resources/static/images/logo-pti-small.png"));
			}
		};

		this.mailSender.send( preparator );
		
		return new AsyncResult<>( null );
	}

	@Async
	@Override
	public Future<Void> sendPasswordResetNotice( final Usuario usuario )
	{
		return new AsyncResult<>( null );
	}	

}