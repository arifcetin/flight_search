package com.example.flight_search.security;

import com.example.flight_search.entity.Role;
import com.example.flight_search.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JwtUserDetails implements UserDetails {

    public Long user_id;
    private String name;
    private String password;
    private Role role;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails(Long user_id, String name, String password, Role role, Collection<? extends GrantedAuthority> authorities) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.authorities = authorities;
    }

    public static JwtUserDetails create(User user) {
        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        authoritiesList.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return new JwtUserDetails(user.getUser_id(), user.getUserName(), user.getPassword(),user.getRole(), authoritiesList);
    }
    public Long getId() {
        return user_id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    public Role getRole() {
        return role;
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
