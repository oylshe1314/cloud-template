package com.sk.op.application.user.component;

import com.sk.op.application.redis.reo.UserReo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String phone;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetails(UserReo userReo) {
        this(userReo, null);
    }

    public UserDetails(UserReo userReo, Collection<? extends GrantedAuthority> authorities) {
        this(userReo.getId(), userReo.getUsername(), userReo.getPassword(), userReo.getPhone(), userReo.getEmail(), authorities);
    }

    public UserDetails(Long id, String username, String password, String phone, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.authorities = authorities;
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
