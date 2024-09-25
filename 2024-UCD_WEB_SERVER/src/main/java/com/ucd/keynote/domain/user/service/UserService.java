package com.ucd.keynote.domain.user.service;

import com.ucd.keynote.common.service.AuthService;
import com.ucd.keynote.domain.user.dto.SignUpRequestDTO;
import com.ucd.keynote.domain.user.dto.UserResponseDTO;
import com.ucd.keynote.domain.user.dto.UsernameUpdateResponseDTO;
import com.ucd.keynote.domain.user.entity.UserEntity;
import com.ucd.keynote.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthService authService;


    // 회원 가입 서비스
    public void userSign(SignUpRequestDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();

/*        // 이메일 존재하는지 확인
        if (userRepository.existsByEmail(email)) {
            // 이메일 존재할 시 예외 처리
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }*/

        UserEntity user = new UserEntity();

        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password)); // 암호화를 진행하여 저장
        user.setEmail(email);
        user.setRole("ROLE_ADMIN");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    // 사용자 정보 가져오기
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

    // 사용자 이름 변경 서비스
    public UsernameUpdateResponseDTO updateUsername(String newUsername){
        // 현재 로그인 한 사용자 정보 받아오기
        Long userId = authService.getAuthenticatedUser().getUserId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 새로운 사용자 이름 설정
        user.setUsername(newUsername);

        // DB에 저장
        userRepository.save(user);

        // 응답 객체 생성
        return UsernameUpdateResponseDTO.builder()
                .userId(user.getUserId())
                .newUsername(user.getUsername())
                .build();
    }

}
