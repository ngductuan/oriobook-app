package com.project.oriobook.common.components.filters;

import com.project.oriobook.common.components.helpers.JwtTokenHelper;
import com.project.oriobook.common.constants.RouteConst;
import com.project.oriobook.common.exceptions.AuthException;
import com.project.oriobook.common.utils.CommonUtil;
import com.project.oriobook.modules.user.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;

    private final UserDetailsService userDetailsService;
    private final JwtTokenHelper jwtTokenHelper;
    private final CommonUtil commonUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (isBypassToken(request)) {
                filterChain.doFilter(request, response); // enable bypass
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new AuthException.TokenEmpty(request.getRequestURI());
            }
            final String token = authHeader.substring(7);

            // *** Check exists in redis => TokenInValid

            // Check token expired
            if(jwtTokenHelper.isTokenExpired(token, request)){
                throw new AuthException.TokenExpired(request.getRequestURI());
            }

            final String extractEmail = jwtTokenHelper.extractEmail(token);
            if (extractEmail != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(extractEmail);

                if (userDetails == null) {
                    throw new AuthException.TokenInvalid(request.getRequestURI());
                }

                if (jwtTokenHelper.validateToken(token, userDetails, request)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    throw new AuthException.TokenExpired(request.getRequestURI());
                }
            }

            filterChain.doFilter(request, response); // enable bypass
        }
        catch (Exception e) {
            commonUtil.responseFilterException(e, response);
        }
    }

    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        RouteConst routeConst = new RouteConst(apiPrefix);

        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        for (Pair<String, String> token : routeConst.getBypassRoutes()) {
            String path = token.getFirst();
            String method = token.getSecond();
            // Check if the request "path" and "method" match any pair in the bypassTokens list
            // ** in api route, *. stands for /** in matches function => Replace
            if (requestPath.matches(path.replace("**", ".*"))
                    && requestMethod.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }
}
