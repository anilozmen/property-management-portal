package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApplicationUserDetail implements UserDetails {
    private final String username;
    private final String password;
    private final String role;
    private final long id;

    public ApplicationUserDetail(User user) {
        username = user.getEmail();
        password = user.getPassword();
        role = user.getUserType();
        id = user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> role);

        return authorities;
    }

    public long getId() {
        return id;
    }

    public boolean isOwner() {
        return role == "OWNER";
    }

    public boolean isAdmin() {
        return role == "ADMIN";
    }

    public boolean isCustomer() {
        return role == "CUSTOMER";
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
