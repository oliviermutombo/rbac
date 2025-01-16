package com.example.rbac.config;

import jakarta.servlet.ServletException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String rolesHeader = request.getHeader("X-Roles");
        String employeeNumber = request.getHeader("X-Employee-Number");

        if (rolesHeader != null && employeeNumber != null) {
            Map<String, List<SimpleGrantedAuthority>> businessUnitRoles = Arrays.stream(rolesHeader.split(","))
                    .map(String::trim)
                    .collect(Collectors.groupingBy(
                            role -> role.split("_")[1],  // Group by Business Unit part
                            Collectors.mapping(
                                    role -> new SimpleGrantedAuthority("ROLE_" + role.split("_")[2]), // Prefix Role part with ROLE_
                                    //role -> new SimpleGrantedAuthority(role.split("_")[2]),  // Map to Role part
                                    Collectors.toList()
                            )
                    ));

            CustomUserDetails userDetails = new CustomUserDetails(employeeNumber, businessUnitRoles);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Debug logging
            System.out.println("Roles Set: " + userDetails.getAuthorities());
        }

        filterChain.doFilter(request, response);
    }
}


