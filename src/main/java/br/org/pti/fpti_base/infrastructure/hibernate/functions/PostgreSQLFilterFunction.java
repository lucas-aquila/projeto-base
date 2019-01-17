package br.org.pti.fpti_base.infrastructure.hibernate.functions;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

public class PostgreSQLFilterFunction implements SQLFunction
{
    /**
     * Ex: FILTER(:filter, usuario.id, usuario.email, usuario.nome) = TRUE
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public String render( Type firstArgumentType, List arguments, SessionFactoryImplementor factory ) throws QueryException
    {
    	final String query = this.renderCast( (String) arguments.get(0) );
    	final List<String> fields = (List<String>) arguments.stream().skip(1)
    			.map( field -> this.renderCast( (String) field ) )
    			.collect( Collectors.toList() );
		
		return String.format( "FILTER(%s, %s)", query, String.join( ", ", fields) );
    }
    
    /**
     * 
     * @param field
     * @return
     */
    private String renderCast( String field )
    {
    	return String.format( "cast(%s as text)", field );
    }
    
    /**
     * 
     */
    @Override
    public Type getReturnType( Type firstArgumentType, Mapping mapping ) throws QueryException
    {
    	return new BooleanType();
    }
    
    /**
     * 
     */
    @Override
    public boolean hasArguments()
    {
    	return true;
    }

    /**
     * 
     */
    @Override
    public boolean hasParenthesesIfNoArguments()
    {
    	return false;
    }
}