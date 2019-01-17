package br.org.pti.fpti_base.domain.entity.generic;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import br.org.pti.fpti_base.Application;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(schema = Application.AUDIT_SCHEMA)
@RevisionEntity(EntityTrackingRevisionListener.class)
public class Revision implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1770838608009757264L;

	/**
	 * 
	 */
	@RevisionNumber
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * 
	 */
	@RevisionTimestamp
	private long timestamp;

	/**
	 * 
	 */
	private Long userId;
}