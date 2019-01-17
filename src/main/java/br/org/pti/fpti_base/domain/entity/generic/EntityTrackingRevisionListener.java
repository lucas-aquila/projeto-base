package br.org.pti.fpti_base.domain.entity.generic;

import java.io.Serializable;

import org.hibernate.envers.RevisionType;

public class EntityTrackingRevisionListener implements org.hibernate.envers.EntityTrackingRevisionListener
{
	/**
	 * 
	 */
	@Override
	public void newRevision( Object revisionEntity ) {}

	/**
	 * 
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void entityChanged( Class entityClass, String entityName, Serializable entityId, RevisionType revisionType, Object revisionEntity ) {}
}