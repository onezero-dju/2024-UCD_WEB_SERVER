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
    private final UserRepository userRepository; // 데이터 베이스에 접근할 userRepository
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 가져온 이메일로 회원 데이터 저장
        UserEntity userData = userRepository.findByEmail(email);

        if(userData != null){
            return new CustomUserDetails(userData);
        }
        return null;
    }


}
