package com.ucd.keynote.domain.user.controller;

import com.ucd.keynote.domain.user.dto.ApiResponseDTO;
import com.ucd.keynote.domain.user.dto.CustomUserDetails;
import com.ucd.keynote.domain.user.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getCurrentUser() {
        // SecurityContextHolder에서 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // UserResponseDTO 객체 생성
        UserResponseDTO userResponse = UserResponseDTO.builder()
                .userId(userDetails.getUserEntity().getUserId())
                .userName(userDetails.getUsername())
                .email(userDetails.getEmail())
                .role(userDetails.getUserEntity().getRole())
                .build();

        // ApiResponseDTO 객체 생성
        ApiResponseDTO<UserResponseDTO> response = ApiResponseDTO.<UserResponseDTO>builder()
                .code(200)
                .message("success")
                .data(userResponse)
                .build();

        // 응답 반환
        return ResponseEntity.ok(response);
    }
}
