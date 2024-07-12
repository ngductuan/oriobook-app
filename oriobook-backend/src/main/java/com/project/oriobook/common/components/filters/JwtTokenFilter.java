package com.project.oriobook.common.components.filters;

import com.project.oriobook.common.components.helpers.CommonHelper;
import com.project.oriobook.common.components.helpers.JwtTokenHelper;
import com.project.oriobook.common.constants.RouteConst;
import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.exceptions.base.JwtException;
import com.project.oriobook.common.exceptions.web_security.FixJwtException;
import com.project.oriobook.modules.user.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
                // request.setAttribute("pathReq", request.getRequestURI());
                // response.sendError(
                //         HttpServletResponse.SC_UNAUTHORIZED,
                //         CommonHelper.transformMapToJson(
                //                 new JwtException(ErrorCodeEnum.AUTH_BEARER_NULL,
                //                         request.getRequestURI()).getErrorResponse()
                //         ));
                throw new InsufficientAuthenticationException("authHeader null or not started with Bearer");
                // throw new JwtException(ErrorCodeEnum.AUTH_BEARER_NULL, request.getRequestURI());
                // return;
            }
            final String token = authHeader.substring(7);
            final String extractEmail = jwtTokenHelper.extractEmail(token);
            if (extractEmail != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(extractEmail);

                if (userDetails == null) {
                    System.out.println("User not found" + userDetails);
                    // throw new AuthException.TokenInvalid();
                    throw new FixJwtException(ErrorCodeEnum.AUTH_INVALID_TOKEN, request.getRequestURI());
                }

                if (jwtTokenHelper.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response); // enable bypass
        }
        // catch (Exception e) {
        //     SecurityContextHolder.clearContext();
        //     throw e;
        // }
        // catch (AuthenticationException e) {
        //     SecurityContextHolder.clearContext();
        //     throw e;
        // }
        catch (Exception e) {
            System.out.println("JwtTokenFilter.doFilterInternal: " + e.getClass().getName());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }
    }

    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        RouteConst routeConst = new RouteConst(apiPrefix);

        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        for (Pair<String, String> token : routeConst.getBypassRoutes()) {
            String path = token.getFirst();
            String method = token.getSecond();
            // Check if the request path and method match any pair in the bypassTokens list
            // ** in api route, *. stands for /** in matches function => Replace
            if (requestPath.matches(path.replace("**", ".*"))
                    && requestMethod.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }
}
