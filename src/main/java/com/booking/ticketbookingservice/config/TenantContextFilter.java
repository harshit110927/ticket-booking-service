package com.booking.ticketbookingservice.config;

import com.booking.ticketbookingservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TenantContextFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JdbcTemplate jdbcTemplate;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        Long tenantId = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            try {
                tenantId = jwtService.extractTenantId(jwt);
            } catch (Exception e) {
                // Token might be invalid or expired, ignore and proceed
            }
        }

        try {
            if (tenantId != null) {
                jdbcTemplate.execute("SET app.tenant_id = '" + tenantId + "'");
            }
            filterChain.doFilter(request, response);
        } finally {
            jdbcTemplate.execute("SET app.tenant_id = ''");
        }
    }
}