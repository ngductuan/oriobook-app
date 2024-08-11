package com.project.oriobook.common.configs;

import com.project.oriobook.common.components.filters.JwtTokenFilter;
import com.project.oriobook.common.constants.RouteConst;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final CorsConfigurationSource corsConfigurationSource;

    @Value("${api.prefix}")
    private String apiPrefix;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers
                        .xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                        .contentSecurityPolicy(csp -> csp.policyDirectives("script-src 'self'"))
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    RouteConst routeConst = new RouteConst(apiPrefix);
                    List<Pair<String, String>> publicPaths = routeConst.getBypassRoutes();

                    publicPaths.forEach(path -> {
                        requests.requestMatchers(path.getFirst(), path.getSecond()).permitAll();
                    });

                    requests.anyRequest().authenticated();
                });

        return http.build();
    }

    // Cookie Same-site configuration (error when run)
    // @Bean
    // public CookieSameSiteSupplier applicationCookiesSameSiteSupplier() {
    //     return CookieSameSiteSupplier.ofStrict();
    // }
}
