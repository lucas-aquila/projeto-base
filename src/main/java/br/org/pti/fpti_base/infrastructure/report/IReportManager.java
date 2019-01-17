package br.org.pti.fpti_base.infrastructure.report;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public interface IReportManager
{
	/**
	 * 
	 * @param parameters
	 * @param reportPath 
	 */
	public ByteArrayOutputStream exportToPDF( Map<String, Object> parameters, String reportPath );
	
	/**
	 * 
	 * @param parameters
	 * @param reportPath
	 */
	public ByteArrayOutputStream exportToHTML( Map<String, Object> parameters, String reportPath );
	
	/**
	 * 
	 * @param parameters
	 * @param reportPath
	 */
	public ByteArrayOutputStream exportToXML( Map<String, Object> parameters, String reportPath );
	
	/**
	 * 
	 * @param parameters
	 * @param reportPath
	 */
	public ByteArrayOutputStream exportToXLS( Map<String, Object> parameters, String reportPath );
	
	/**
	 * 
	 * @param parameters
	 * @param reportPath
	 * @param configuration
	 * @return
	 */
	public ByteArrayOutputStream exportToXLS( Map<String, Object> parameters, String reportPath, SimpleXlsReportConfiguration configuration );	
	
	/**
	 * 
	 * @param dataSource
	 */
	public void setDataSource( DataSource dataSource );
}
