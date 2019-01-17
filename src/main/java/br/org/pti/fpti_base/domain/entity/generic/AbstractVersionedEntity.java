package br.org.pti.fpti_base.domain.entity.generic;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractVersionedEntity extends AbstractEntity {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -1724863915914011951L;
	
	/**
	 * 
	 */
	@NotNull
    @Version
    @Column(nullable = false)
    private Long version;

	/**
	 * 
	 */
    public AbstractVersionedEntity() {}

    /**
     * 
     * @param id
     */
    public AbstractVersionedEntity(Long id) {
        super(id);
    }
}
