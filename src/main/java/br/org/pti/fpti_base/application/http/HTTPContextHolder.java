package br.org.pti.fpti_base.application.http;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HTTPContextHolder
{
	@SuppressWarnings("unused")
	public static String getServerURL()
	{
		final ServletRequestAttributes servletRequestAttributes = ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes();
		
		if ( servletRequestAttributes == null ) 
		{
			throw new IllegalStateException( "Was not possible to get the current request. Are you requesting from Web?" );
		}

		final HttpServletRequest servletRequest = servletRequestAttributes.getRequest();
		final String scheme = servletRequest.getScheme();
		final String serverName = servletRequest.getServerName();
		final int portNumber = servletRequest.getServerPort();
		final String contextPath = servletRequest.getContextPath();

		return scheme + "://" + serverName + ( portNumber == 80 ? "" : ":" + portNumber ) + ( contextPath.isEmpty() ? "" : contextPath );
	}
}
