package br.org.pti.fpti_base.application.configuration;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationReadyListenerConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyListenerConfiguration.class);

    @Bean
    public ApplicationListener<ApplicationReadyEvent> getApplicationReadyEvent() {
        return applicationReadyEvent -> {
            LOGGER.info("--------------------------------------------------");

            //Arrays.asList(applicationReadyEvent.getApplicationContext().getEnvironment().getActiveProfiles()).forEach(LOGGER::info);
            List<String> profiles = Arrays.asList(applicationReadyEvent.getApplicationContext().getEnvironment().getActiveProfiles());
            
            //default -> dev 
            if (profiles.isEmpty()) {
            	LOGGER.info("Sistema iniciado com o perfil de configuração: dev");
            }
            
            profiles.forEach(profile ->
            	LOGGER.info("Sistema iniciado com o perfil de configuração: {}", profile)
            );
            LOGGER.info("--------------------------------------------------");
        };
    }
}