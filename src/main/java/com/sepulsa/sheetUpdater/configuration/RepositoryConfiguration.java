package com.sepulsa.sheetUpdater.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;


@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.sepulsa.sheetUpdater.entity"})
@EnableTransactionManagement
public class RepositoryConfiguration {
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        registrationBean.addInitParameter("webAllowOthers", "true");
        return registrationBean;
    }
    
    
}
