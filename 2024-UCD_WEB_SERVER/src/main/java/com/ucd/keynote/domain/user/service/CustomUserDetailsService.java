package com.ucd.keynote.domain.user.service;

import com.ucd.keynote.domain.user.dto.CustomUserDetails;
import com.ucd.keynote.domain.user.entity.UserEntity;
import com.ucd.keynote.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByEmail(email);
        if(userData != null){
            return new CustomUserDetails(userData);
        }
        return null;
    }

    /*public UserDetails loadUserByEmail(String email){
        UserEntity userData = userRepository.findByEmail(email);
        if(userData != null){
            return new CustomUserDetails(userData);
        }
        return null;
    }*/

}
