package com.ucd.keynote.domain.user.service;

import com.ucd.keynote.domain.common.service.AuthService;
import com.ucd.keynote.domain.user.dto.SignUpRequestDTO;
import com.ucd.keynote.domain.user.dto.UserResponseDTO;
import com.ucd.keynote.domain.user.entity.UserEntity;
import com.ucd.keynote.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 사용자 정보 가져오기
    public UserResponseDTO getCurrentUser(){
        // 로그인 된 사용자 정보 가져오기
        UserEntity user = authService.getAuthenticatedUser();

        // UserResponseDTO 객체 생성 후 반환
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

    // 회원 가입
    public void userSign(SignUpRequestDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();

        // 이메일 존재하는지 확인
        Boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {
            // 이메일 존재할 시 예외 처리
            return;
        }

        UserEntity user = new UserEntity();

        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password)); // 암호화를 진행하여 저장
        user.setEmail(email);
        user.setRole("ROLE_ADMIN");

        userRepository.save(user);
    }

    public UserResponseDTO getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }


}
