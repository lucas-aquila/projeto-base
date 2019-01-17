package br.org.pti.fpti_base.domain.entity.usuario;

import org.springframework.security.core.GrantedAuthority;

public enum Perfil implements GrantedAuthority {

    ADMINISTRADOR, 	// 0
    USUARIO,		// 1
    GERENTE,     	// 2
    PATROCINADOR;	//3

    public static final String ADMINISTRADOR_VALUE = "ADMINISTRADOR";
    public static final String USUARIO_VALUE = "USUARIO";
    public static final String GERENTE_VALUE = "GERENTE";
    public static final String PATROCINADOR_VALUE = "PATROCINADOR";
    
    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.GrantedAuthority#getAuthority()
     */
    @Override
    public String getAuthority() {
        return this.name();
    }
}
