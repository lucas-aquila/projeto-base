package br.org.pti.fpti_base;

import javax.validation.Validator;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import br.org.pti.fpti_base.application.i18n.ResourceBundleMessageSource;
import br.org.pti.fpti_base.infrastructure.report.IReportManager;
import br.org.pti.fpti_base.infrastructure.report.jasper.JasperReportManager;

@EnableAsync
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
    	//configureApplication(new SpringApplicationBuilder()).run(args);
    	SpringApplication.run(Application.class, args);
    }

    /**
     *
     */
    public static final String AUDIT_SCHEMA = "auditing";

    /**
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		return builder.sources(Application.class).bannerMode(Banner.Mode.OFF);
	}
	
    /**
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setAlwaysUseMessageFormat(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("classpath:i18n/exceptions", "classpath:i18n/labels", "classpath:i18n/messages");
        return messageSource;
    }

    /**
     * @return
     */
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * @return
     */
    @Bean
    public IReportManager reportManager() {
        return new JasperReportManager();
    }
}
