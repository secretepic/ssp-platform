package com.boyu.config.security;

import com.boyu.entity.system.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private List<String> permList;

    public static UserPrincipal build(UserEntity user) {
        return new UserPrincipal(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                null
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permList.stream()
                .map(SimpleGrantedAuthority::new) // 包装成Security的权限对象
                .collect(Collectors.toList());
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
