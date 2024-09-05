package com.ucd.keynote.domain.user.service;

import com.ucd.keynote.domain.common.service.AuthService;
import com.ucd.keynote.domain.user.dto.UserDTO;
import com.ucd.keynote.domain.user.dto.UserResponseDTO;
import com.ucd.keynote.domain.user.entity.UserEntity;
import com.ucd.keynote.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final AuthService authService;

    // 사용자 정보 가져오기
    public UserResponseDTO getCurrentUser(){
        // 로그인 된 사용자 정보 가져오기
        UserEntity authenticateUser = authService.getAuthenticatedUser();

        // UserResponseDTO 객체 생성 후 반환
        return UserResponseDTO.builder()
                .userId(authenticateUser.getUserId())
                .userName(authenticateUser.getUsername())
                .email(authenticateUser.getEmail())
                .role(authenticateUser.getRole())
                .build();
    }

}
