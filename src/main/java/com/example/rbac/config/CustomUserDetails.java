package com.example.rbac.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private String employeeNumber;
    private Map<String, List<SimpleGrantedAuthority>> businessUnitRoles;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String employeeNumber, Map<String, List<SimpleGrantedAuthority>> businessUnitRoles) {
        this.employeeNumber = employeeNumber;
        this.businessUnitRoles = businessUnitRoles;
        this.authorities = businessUnitRoles.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public Map<String, List<SimpleGrantedAuthority>> getBusinessUnitRoles() {
        return businessUnitRoles;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // not used
    }

    @Override
    public String getUsername() {
        return employeeNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

