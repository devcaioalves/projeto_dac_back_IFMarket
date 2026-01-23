package io.github.devcaioalves.projetodacbackifmarket.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUserDetailsServices detailsService;

    public JwtAuthFilter(JwtUserDetailsServices detailsService) {
        this.detailsService = detailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION);

        if (token == null || !token.startsWith(JwtUtils.JWT_BEARER)) {
            log.debug("JWT Token ausente ou não começa com 'Bearer '");
            filterChain.doFilter(request, response);
            return;
        }

        if (!JwtUtils.isTokenValid(token)) {
            log.warn("JWT Token inválido ou expirado!");
            filterChain.doFilter(request, response);
            return;
        }

        String email = JwtUtils.getEmailFromToken(token);
        String role = JwtUtils.getRoleFromToken(token);

        if (email != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            toAuth(request, email, role);
        }

        filterChain.doFilter(request, response);
    }

    private void toAuth(HttpServletRequest request, String email, String role) {
        var authority = new SimpleGrantedAuthority("ROLE_" + role);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(email, null, List.of(authority));
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}


