package com.project.oriobook.common.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.oriobook.common.exceptions.web_security.AccessDeniedException;
import com.project.oriobook.common.exceptions.web_security.AuthenticationException;
import com.project.oriobook.common.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableMethodSecurity
// @EnableWebSecurity(debug = true)
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final JwtTokenFilter jwtTokenFilter;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    //swagger
                                    "/api-docs",
                                    "/api-docs/**",
                                    "/swagger-resources",
                                    "/swagger-resources/**",
                                    "/configuration/ui",
                                    "/configuration/security",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/webjars/swagger-ui/**",
                                    "/swagger-ui/index.html"
                            ).permitAll()
                            .requestMatchers(
                                    String.format("%s/auth/**", apiPrefix)).permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/products/**", apiPrefix)).permitAll()
                            .anyRequest()
                            .authenticated();
                            // .requestMatchers(GET,
                            //         String.format("%s/categories/**", apiPrefix)).permitAll()
                            //

                            //
                            // .requestMatchers(GET,
                            //         String.format("%s/products/images/*", apiPrefix)).permitAll()
                            //
                            // .requestMatchers(GET,
                            //         String.format("%s/orders/**", apiPrefix)).permitAll()
                            //
                            // .requestMatchers(GET,
                            //         String.format("%s/order_details/**", apiPrefix)).permitAll()

                })
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler(objectMapper))
                        .authenticationEntryPoint(authenticationEntryPoint(objectMapper))
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
        return new AccessDeniedException(objectMapper);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
        return new AuthenticationException(objectMapper);
    }
}
