package com.sepulsa.sheetUpdater.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;


@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.sepulsa.sheetUpdater.entity"})
@EnableTransactionManagement
public class RepositoryConfiguration extends WebMvcConfigurationSupport  {
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        registrationBean.addInitParameter("webAllowOthers", "true");
        return registrationBean;
    }
    
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("**/*.css", "**/*.js", "**/*.map", "*.html").addResourceLocations("classpath:META-INF/resources/").setCachePeriod(0);
    }
    
}
