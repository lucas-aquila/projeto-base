package br.org.pti.fpti_base.application.configuration;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.hibernate.boot.model.naming.ImplicitIndexColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

/**
 * Custom implicit naming strategy for hibernate 5.
 * <p/>
 * Necessary because the new hibernate version introduced new semantics and new interfaces and configuration of
 * the naming strategy which is responsible for the determination of the names of database objects (tables, fields,
 * constraints, indices and others) that are not explicitly named.
 */
public class ConstraintsImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

	private static final long serialVersionUID = 4151802139359661918L;
	public static final ImplicitNamingStrategy INSTANCE = new ConstraintsImplicitNamingStrategy();
	
	public ConstraintsImplicitNamingStrategy() {
	}
	
	
	@Override
	public Identifier determineListIndexColumnName(ImplicitIndexColumnNameSource source) {
        System.out.println("NamingStratLIX ===========>  "+source.toString());
		return toIdentifier(
				transformAttributePath( source.getPluralAttributePath() ) + "_ORDER",
				source.getBuildingContext()
		);
	}
	
/*	@Override
	public Identifier determineBasicColumnName(ImplicitBasicColumnNameSource source) {
		// JPA states we should use the following as default:
		//     "The property or field name"
		// aka:
		//     The unqualified attribute path.
        System.out.println("NamingStratCN ===========>  "+source.getAttributePath().getProperty());
		return toIdentifier( transformAttributePath( source.getAttributePath() ), source.getBuildingContext() );
	}

	
	@Override
	public Identifier determineListIndexColumnName(ImplicitIndexColumnNameSource source) {
        System.out.println("NamingStratLIX ===========>  "+source.toString());
		return toIdentifier(
				transformAttributePath( source.getPluralAttributePath() ) + "_ORDER",
				source.getBuildingContext()
		);
	}
	
	@Override
	public Identifier determineTenantIdColumnName(ImplicitTenantIdColumnNameSource source) {
        System.out.println("NamingStratTEN ===========>  "+source.toString());
		return toIdentifier(
				source.getBuildingContext().getMappingDefaults().getImplicitTenantIdColumnName(),
				source.getBuildingContext()
		);
	}
	


     //the original strategy would use the table name which often is set explicitly (e.g. "USERS" for the "User" class)
     //but we need the original class name which is what EJB3NamingStrategy seemingly was using ("USER" instead of "USERS").

    @Override
    public Identifier determineCollectionTableName(ImplicitCollectionTableNameSource source) {
        String owningClassName = source.getOwningEntityNaming().getClassName();
        String owningTableName = owningClassName.substring(owningClassName.lastIndexOf('.') + 1);
        Identifier identifier = toIdentifier(
                owningTableName + "_" + transformAttributePath(source.getOwningAttributePath()),
                source.getBuildingContext()
        );
        if (source.getOwningPhysicalTableName().isQuoted()) {
            identifier = Identifier.quote(identifier);
        }
        return identifier;

    }

    // Same as above, base new identifier on class name instead of declared table name. Again copied and changed code from super

    @Override
    public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
        final String name;

        if (source.getNature() == ImplicitJoinColumnNameSource.Nature.ELEMENT_COLLECTION
                || source.getAttributePath() == null) {

            String referencedClassName = source.getEntityNaming().getClassName();
            String referencedTableName = referencedClassName.substring(referencedClassName.lastIndexOf('.') + 1);

            name = referencedTableName
                    + '_'
                    + source.getReferencedColumnName().getText();
        } else {
            name = transformAttributePath(source.getAttributePath())
                    + '_'
                    + source.getReferencedColumnName().getText();
        }

        return toIdentifier(name, source.getBuildingContext());
    }
    */

    /**
     * Replicate hibernate 4.3's constraint naming strategy in EJBNamingStrategy, which differs in the following
     * ways:
     * - include underscore between prefix and hash value
     * - prefix unique indices also with UK_ instead of IDX_
     * - do not include the referenced table name in the hash value generation for foreign keys (do not use
     * generateHashedFkName method but instead stay with generateHashedConstraintName).
     */

    @Override
    public Identifier determineForeignKeyName(ImplicitForeignKeyNameSource source) {
        //System.out.println("NamingStratFK ===========> "+source.getTableName());
        final String tableName = source.getTableName().getText();
        final String referencedTableName = source.getColumnNames().get(0).getText();//source.getReferencedTableName().getText();
        
        return toIdentifier("FK_" + tableName + "_" + referencedTableName, source.getBuildingContext());
    }

    //Não funciona abaixo https://hibernate.atlassian.net/browse/HHH-12160: 
    /*
	@Override
	public Identifier determinePrimaryKeyJoinColumnName(ImplicitPrimaryKeyJoinColumnNameSource source) {
		// JPA states we should use the following as default: 
		// 		"the same name as the primary key column [of the referenced table]"
        System.out.println("NamingStratPK ===========> "+source.getReferencedPrimaryKeyColumnName().getText());
		return source.getReferencedPrimaryKeyColumnName();
	}
    
    @Override
    public Identifier determineUniqueKeyName(ImplicitUniqueKeyNameSource source) {
        System.out.println("NamingStratUK ===========> "+source.getTableName());
        return generateHashedConstraintName(source, "UKs_");	
    }

    @Override
    public Identifier determineIndexName(ImplicitIndexNameSource source) {
        System.out.println("NamingStratIX ===========> "+source.getTableName());
        return generateHashedConstraintName(source, "UKs_");
    }

    
    
    private Identifier generateHashedConstraintName(ImplicitConstraintNameSource source, String prefix) {
        return toIdentifier(
                NamingHelper.INSTANCE.generateHashedConstraintName(
                        prefix,
                        source.getTableName(),
                        source.getColumnNames()
                ),
                source.getBuildingContext()
        );
    }
    */

}