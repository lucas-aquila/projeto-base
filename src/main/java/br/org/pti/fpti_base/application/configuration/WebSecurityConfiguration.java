package br.org.pti.fpti_base.application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import br.org.pti.fpti_base.application.security.AuthenticationFailureHandler;
import br.org.pti.fpti_base.application.security.AuthenticationSuccessHandler;

@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     *
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     *
     */
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     *
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();

        httpSecurity
                .authorizeRequests().antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("senha")
                .loginPage("/authentication")
                .loginProcessingUrl("/authenticate")
                .failureHandler(this.authenticationFailureHandler)
                .successHandler(this.authenticationSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    /**
     * Override this method to configure {@link WebSecurity}. For example, if you wish to
     * ignore certain requests.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/favicon.ico", "/static/**", "/modules/**", "/bundles/**");
    }
}
