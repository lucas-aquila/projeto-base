package br.org.pti.fpti_base.infrastructure.hibernate.dialect;

import org.hibernate.dialect.PostgreSQL9Dialect;

import br.org.pti.fpti_base.infrastructure.hibernate.functions.PostgreSQLFilterFunction;

public class PostgreSQLDialect extends PostgreSQL9Dialect
{
	public PostgreSQLDialect() 
	{
		super.registerFunction("FILTER", new PostgreSQLFilterFunction());
	}
}
