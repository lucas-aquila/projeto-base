package br.org.pti.fpti_base.domain.entity.usuario;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.org.pti.fpti_base.domain.entity.generic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Audited
@EqualsAndHashCode(callSuper = true, exclude = {"perfis"})
@NoArgsConstructor
public class Usuario extends AbstractEntity implements UserDetails, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8694957669767267811L;

	/**
	 * Máximo de tentativas frustradas de login para bloqueio do usuário
	 */
	private static final int MAX_ATTEMPTS = 5;
	
	/**
	 * 
	 */
	@NotBlank
    @Column(nullable = false, length = 100)
    private String nome;
	    
	/**
	 * 
	 */
    @Email
    @NotBlank
    @Column(nullable = false, length = 150, unique = true)
    private String email;
    
    /**
     * 
     */
    @NotNull
    @Column(nullable = false)
    private Boolean status;

    /**
     * 
     */
    @JsonProperty(access = Access.WRITE_ONLY)
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&+,./])[A-Za-z\\d$@$!%*#?&+,./]{8,}$", flags = Flag.UNICODE_CASE,
    		 message = "A senha deve conter ao menos 8 caracteres com letras, números e um caractere especial.")
    @Column(nullable = false, length = 100)
    private String senha;

    /**
     * 
     */
    @Column(columnDefinition = "int2")
    private Integer tentativasLogin;

    /**
     * 
     */
    @Column
    private LocalDateTime ultimaTentativaLogin;
    
    
    /**
     * 
     */
    @NotNull
    @ElementCollection(targetClass = Perfil.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_perfil", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Set<Perfil> perfis = new HashSet<>();

    /**
     * 
     */
    @Column
    private LocalDateTime ultimoAcesso;

    /**
     * 
     */
    @Column
    private String codigo;

    /**
     * @param id
     */
    public Usuario( Long id ) {
        super( id );
    }
    
    /**
     * 
     * @param id
     * @param nome
     */
    public Usuario( Long id, String nome ) {
    	super( id );
    	this.nome = nome;
    }
    
    /**
     * 
     * @param id
     * @param nome
     * @param login
     * @param status
     * @param organizacaoId
     * @param organizacaoNome
     */
    public Usuario( Long id, String nome, String email, Boolean status) {//, Long organizacaoId, String organizacaoNome ) {
        super( id );
        this.nome = nome;
        this.email = email;
        this.status = status;
        //this.setOrganizacao(new Organizacao( organizacaoId, organizacaoNome ));
    }
    
    /**
     * Registra a quantidade de tentativas de login do usuário para um possível bloqueio de acesso
     */
    public void registerFailedLogin() {
    	this.tentativasLogin = this.tentativasLogin != null ? this.tentativasLogin+1 : 1;
    	this.ultimaTentativaLogin = LocalDateTime.now();
    }
    
    /**
     * 
     * @return
     */
    public Boolean getIsAdministrador() {
        if ( this.perfis == null ) {
        	return null;
        }
        
        return this.perfis.stream().anyMatch(perfil -> perfil.equals(Perfil.ADMINISTRADOR));
    }
    
    /**
     * 
     */
    @Override
    @JsonIgnore
    @Transient
	public Set<GrantedAuthority> getAuthorities() {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        
        if ( this.perfis == null ) {
        	return null;
        }
        
        this.perfis.forEach(perfil -> {
        	authorities.add( perfil );
        	
        	if ( perfil.equals( Perfil.ADMINISTRADOR ) ) {
        		authorities.add( Perfil.GERENTE );
        		authorities.add( Perfil.USUARIO );
        	}
        });
        
        return authorities;
    }
    
    @Override
    @JsonIgnore
    @Transient
    public String getPassword() {
        return this.senha;
    }

    @Override
    @JsonIgnore
    @Transient
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    @Transient
    public boolean isAccountNonLocked() {
    	if (this.ultimaTentativaLogin==null || this.tentativasLogin==null) {
    		return true;
    	}
    	
    	if (this.tentativasLogin>=MAX_ATTEMPTS) {
    		if (LocalDateTime.now().isBefore(this.ultimaTentativaLogin.plusHours(3))) {
    			return false;
    		} else {
    			return true;
    		}
    	}
    	
    	return true;
    }

    @Override
    @JsonIgnore
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    @Transient
    public boolean isEnabled() {
        return this.status;
    }
}
