package br.org.pti.fpti_base.infrastructure.report.jasper;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.Assert;

import br.org.pti.fpti_base.infrastructure.report.IReportManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class JasperReportManager implements IReportManager
{
	/**
	 * 
	 */
	public final static String SOURCE_FILE_EXTENSION = ".jrxml";
	/**
	 * 
	 */
	public final static String COMPILED_FILE_EXTENSION = ".jasper";
	
	/**
	 *
	 */
	private static final Logger LOG = Logger.getLogger( JasperReportManager.class.getName() );
	
    /**
     * 
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 
     * @param reportPath
     * @return
     */
    private JasperReport createJasperReport( String reportPath )
    {
    	Assert.hasLength( reportPath, "The path to the report template must not be null or empty." );
    	Assert.isTrue( reportPath.contains( COMPILED_FILE_EXTENSION ) || reportPath.contains( SOURCE_FILE_EXTENSION ), "The path to the report template must be a correct .jrxml or .jasper file." );
    	
    	//get the input stream of file
    	final InputStream reportStream = this.getClass().getResourceAsStream( reportPath );
    	Assert.notNull( reportStream, "Was not possible to find the report template: '"+reportPath+"'" );
    	
    	JasperReport jasperReport;
    	
    	//should we compile this report?
    	if ( reportPath.contains( SOURCE_FILE_EXTENSION ) )
    	{
    		LOG.warning( "Do not compile a report out of development environment. Compiling report: '"+reportPath+"'..." );
    		try
			{
    			final long now = System.currentTimeMillis();
				jasperReport = JasperCompileManager.compileReport( reportStream );
				LOG.info("Time to compile: "+ (System.currentTimeMillis()-now) );
			}
			catch ( JRException e )
			{
				throw new IllegalArgumentException( "Was not to possible to compile the report: '"+reportPath+"'", e );
			}
    	}
    	//this file is already compile, so we just load the jasper file
    	else
    	{
    		try
			{
				jasperReport = (JasperReport) JRLoader.loadObject( reportStream );
			}
			catch ( JRException e )
			{
				throw new IllegalArgumentException( "Was not possible to load the compiled report: '"+reportPath+"'", e );
			}
    	}
    	
    	return jasperReport;
		
    }
    
    /**
     * 
     * @param jasperReport
     * @param parameters
     * @return
     */
    private JasperPrint fillReport( JasperReport jasperReport, Map<String, Object> parameters )
    {
    	Assert.notNull( this.dataSource, "The data source must be not null to generate a report. Please check if the bean is correctly configured." );
    	Assert.notNull( jasperReport, "The jasper report must not be null." );
    	
    	Connection connection = null;
    	try
    	{
    		connection = DataSourceUtils.getConnection( this.dataSource );
    		return JasperFillManager.fillReport( jasperReport, parameters, connection );
    	}
    	catch ( JRException e )
    	{
    		throw new IllegalStateException( "Was not possible to fill the report: '"+jasperReport.getName()+"'", e );
    	}
    	finally 
    	{
    		if ( connection != null )
    		{
    			DataSourceUtils.releaseConnection( connection, this.dataSource );
    		}
    	}
    }
    
	/**
	 * 
	 */
    @Override
    public ByteArrayOutputStream exportToPDF( Map<String, Object> parameters, String reportPath )
	{
    	final JasperReport jasperReport = this.createJasperReport( reportPath );
		final JasperPrint jasperPrint = this.fillReport( jasperReport, parameters );
		
		try
		{
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
			return byteArrayOutputStream;
		}
		catch ( JRException e )
		{
			throw new IllegalStateException( "Was not possible generate a PDF stream of report: '"+jasperReport.getName()+"'", e );
		}
	}
    
	/**
	 * 
	 */
	@Override
	public ByteArrayOutputStream exportToHTML( Map<String, Object> parameters, String reportPath )
	{
		throw new NotImplementedException( "Not implemented yet. HTML report must be generated and zipped as a OutputStream." );
	}
	
	/**
	 * 
	 */
	@Override
	public ByteArrayOutputStream exportToXLS( Map<String, Object> parameters, String reportPath )
	{
		SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
		configuration.setOnePagePerSheet(true);
		configuration.setDetectCellType(true);
		configuration.setCollapseRowSpan(true);
		configuration.setWhitePageBackground(false);
	    return this.exportToXLS( parameters, reportPath, configuration );   
	}	
	
	/**
	 * 
	 */
	@Override
	public ByteArrayOutputStream exportToXLS( Map<String, Object> parameters, String reportPath, SimpleXlsReportConfiguration configuration )
	{
		final JasperReport jasperReport = this.createJasperReport( reportPath );
		final JasperPrint jasperPrint = this.fillReport( jasperReport, parameters );
	
		try
		{
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			JRXlsExporter exporter = new JRXlsExporter();
		
			exporter.setExporterInput( new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		
			return byteArrayOutputStream;
		}
		catch ( Exception e )
		{
			throw new IllegalStateException( "Was not possible generate a XLS stream of report: '"+jasperReport.getName()+"'", e );
		}
	}
	
	/**
	 * 
	 */
	@Override
	public ByteArrayOutputStream exportToXML( Map<String, Object> parameters, String reportPath )
	{
    	final JasperReport jasperReport = this.createJasperReport( reportPath );
		final JasperPrint jasperPrint = this.fillReport( jasperReport, parameters );
		
		try
		{
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			JasperExportManager.exportReportToXmlStream(jasperPrint, byteArrayOutputStream);
			return byteArrayOutputStream;
		}
		catch ( JRException e )
		{
			throw new IllegalStateException( "Was not possible generate a XML stream of report: '"+jasperReport.getName()+"'", e );
		}
	}
	
	/**
	 * 
	 */
	public void setDataSource( DataSource dataSource )
	{
		Assert.notNull( dataSource, "The data source must be not null." );
		this.dataSource = dataSource;
	}
}
