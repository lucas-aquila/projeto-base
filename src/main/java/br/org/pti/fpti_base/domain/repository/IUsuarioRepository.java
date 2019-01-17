package br.org.pti.fpti_base.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.org.pti.fpti_base.domain.entity.usuario.Perfil;
import br.org.pti.fpti_base.domain.entity.usuario.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	
	/**
	 * 
	 * @param filter
	 * @param status
	 * @param pageable
	 * @return
	 */
    @Query("SELECT new Usuario( usuario.id, usuario.nome, usuario.email, usuario.status) "
    	//	+ "organizacao.id, organizacao.nome ) "
    		+ "FROM Usuario usuario "
    	//	+ "LEFT JOIN usuario.organizacao organizacao "
    		+ "WHERE ( ( FILTER(:filter, usuario.nome, usuario.email ) = TRUE ) "
    		+ "AND ( usuario.status = :status OR :status = NULL ) )")
    Page<Usuario> listByFilters(@Param("filter") String filter, @Param("status") Boolean status, Pageable pageable);

    /**
     * 
     * @param email
     * @return
     */
    UserDetails findByEmail(final String email);
    
    /**
     * 
     * @param codigo
     * @return
     */
    Usuario findByCodigo(final String codigo);
    
    /**
     * 
     * @param perfil
     * @return
     */
	@Query("FROM Usuario usuario "
			+ "JOIN usuario.perfis perfil "
			+ "WHERE FILTER(:nome, usuario.nome ) = TRUE "
			+ "AND perfil = :perfil "
			+ "AND usuario.status = TRUE "
			+ "ORDER BY usuario.nome")
	List<Usuario> listByNomeAndPerfil(@Param("nome") String nome, @Param("perfil") Perfil perfil);
}
