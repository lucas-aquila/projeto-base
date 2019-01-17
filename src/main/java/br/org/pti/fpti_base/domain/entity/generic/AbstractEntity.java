package br.org.pti.fpti_base.domain.entity.generic;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1016827183036472876L;

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	protected LocalDateTime created;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	protected LocalDateTime updated;
	
	/**
	 * 
	 * @param id
	 */
	public AbstractEntity( Long id )
	{
		this.id = id;
	}
	
	/**
	 * 
	 */
	@PrePersist
	protected void refreshCreatedAndUpdated()
	{
		final LocalDateTime now = LocalDateTime.now();
		this.created = now;
		this.updated = now;
	}
	
	/**
	 * 
	 */
	@PreUpdate
	protected void refreshUpdated()
	{
		this.updated = LocalDateTime.now();
	}
}