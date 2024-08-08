package com.project.oriobook.common.configs.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@Profile("prod")
public class ProdCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("ProdCorsConfig");
        // registry.addMapping("/**")
        //         .allowedOrigins("http://localhost:3000")
        //         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        //         .allowedHeaders("*")
        //         .allowCredentials(true);
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
