package com.project.oriobook.common.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.oriobook.common.components.filters.JwtTokenFilter;
import com.project.oriobook.common.constants.RouteConst;
import com.project.oriobook.common.exceptions.web_security.CustomAccessDeniedHandler;
import com.project.oriobook.common.exceptions.web_security.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

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
                    RouteConst routeConst = new RouteConst(apiPrefix);
                    List<Pair<String, String>> publicPaths = routeConst.getBypassRoutes();

                    publicPaths.forEach(path -> {
                        requests.requestMatchers(path.getFirst(), path.getSecond()).permitAll();
                    });

                    requests.anyRequest().authenticated();
                })
                // .exceptionHandling(exceptionHandling -> {
                //     // System.out.println("exceptionHandling: " + exceptionHandling);
                //     exceptionHandling
                //             .authenticationEntryPoint(authenticationEntryPoint())
                //             .accessDeniedHandler(accessDeniedHandler(objectMapper));
                //   }
                // )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    // @Bean
    // public AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
    //     return new CustomAccessDeniedHandler(objectMapper);
    // }
    //
    // @Bean
    // public AuthenticationEntryPoint authenticationEntryPoint() {
    //     return new CustomAuthenticationEntryPoint();
    // }
}
