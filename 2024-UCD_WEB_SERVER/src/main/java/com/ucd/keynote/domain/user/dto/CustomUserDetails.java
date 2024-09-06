package com.ucd.keynote.domain.user.dto;

import com.ucd.keynote.domain.user.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;
    public CustomUserDetails(UserEntity userEntity){
        this.userEntity = userEntity;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userEntity.getRole();
            }
        });

        return collection;
    }

    public String getEmail(){
        return userEntity.getEmail();
    }

    public UserEntity getUserEntity() {
        return this.userEntity;
    }

    public LocalDateTime getCreatedAt(){
        return userEntity.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt(){
        return userEntity.getUpdatedAt();
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
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
