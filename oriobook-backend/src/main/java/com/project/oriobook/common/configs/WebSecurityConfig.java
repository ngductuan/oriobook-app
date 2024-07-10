package com.project.oriobook.common.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;

// @Configuration
// @EnableMethodSecurity
// @EnableWebSecurity(debug = false)
// @RequiredArgsConstructor
// public class WebSecurityConfig {
//     @Bean
//     public void securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
//                 .authorizeHttpRequests(requests -> {
//                     requests
//                             .requestMatchers(
//                                     String.format("%s/users/register", apiPrefix),
//                                     String.format("%s/users/login", apiPrefix),
//                                     //healthcheck
//                                     String.format("%s/healthcheck/**", apiPrefix),
//
//                                     //swagger
//                                     //"/v3/api-docs",
//                                     //"/v3/api-docs/**",
//                                     "/api-docs",
//                                     "/api-docs/**",
//                                     "/swagger-resources",
//                                     "/swagger-resources/**",
//                                     "/configuration/ui",
//                                     "/configuration/security",
//                                     "/swagger-ui/**",
//                                     "/swagger-ui.html",
//                                     "/webjars/swagger-ui/**",
//                                     "/swagger-ui/index.html"
//
//                             )
//                             .permitAll()
//                             .requestMatchers(GET,
//                                     String.format("%s/roles**", apiPrefix)).permitAll()
//
//                             .requestMatchers(GET,
//                                     String.format("%s/categories/**", apiPrefix)).permitAll()
//
//                             .requestMatchers(GET,
//                                     String.format("%s/products/**", apiPrefix)).permitAll()
//
//                             .requestMatchers(GET,
//                                     String.format("%s/products/images/*", apiPrefix)).permitAll()
//
//                             .requestMatchers(GET,
//                                     String.format("%s/orders/**", apiPrefix)).permitAll()
//
//                             .requestMatchers(GET,
//                                     String.format("%s/order_details/**", apiPrefix)).permitAll()
//
//                             .anyRequest()
//                             .authenticated();
//                     //.anyRequest().permitAll();
//                 })
//                 .csrf(AbstractHttpConfigurer::disable);
//         http.securityMatcher(String.valueOf(EndpointRequest.toAnyEndpoint()));
//         return http.build();
//     }
// }
// public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http
//                 .authorizeRequests()
//                 .antMatchers("/**").permitAll();
//     }
// }
