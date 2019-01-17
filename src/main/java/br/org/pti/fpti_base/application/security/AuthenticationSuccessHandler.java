package br.org.pti.fpti_base.application.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.google.gson.Gson;

import br.org.pti.fpti_base.domain.entity.usuario.Usuario;
import br.org.pti.fpti_base.domain.repository.IUsuarioRepository;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(AuthenticationSuccessHandler.class.getName());

    /**
     *
     */
    @Autowired
    private IUsuarioRepository usuarioRepository;

    /**
     *
     */
    public AuthenticationSuccessHandler() {
        super();
        setRedirectStrategy((request, response, url) -> {});
    }

    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {

            final Usuario usuario = this.usuarioRepository.findById((ContextHolder.getAuthenticatedUser().getId())).get();
            usuario.setUltimoAcesso(LocalDateTime.now());
            usuario.setTentativasLogin(null);
            usuario.setCodigo(null);
            this.usuarioRepository.save(usuario);

            usuario.setSenha(null);
            
            response.setCharacterEncoding("UTF8"); // this line solves the problem
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(usuario)); //TODO Não converte os getters
            
            // Adiciona o usuário na resposta (response)
            // response.getWriter().write( new ObjectMapper().writeValueAsString(usuario) );

        } catch (Exception e) {
            e.printStackTrace();
            LOG.severe("Ocorreu um problema ao atualizar o último login do usuário");
        }
    }
}
