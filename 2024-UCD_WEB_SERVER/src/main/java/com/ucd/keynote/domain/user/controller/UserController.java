package com.ucd.keynote.domain.user.controller;

import com.ucd.keynote.domain.common.dto.ApiResponseDTO;
import com.ucd.keynote.domain.common.dto.CombinedResponseDTO;
import com.ucd.keynote.domain.common.dto.NoDataApiResponseDTO;
import com.ucd.keynote.domain.common.service.AuthService;
import com.ucd.keynote.domain.common.service.CombinedService;
import com.ucd.keynote.domain.user.dto.SignUpRequestDTO;
import com.ucd.keynote.domain.user.dto.UserResponseDTO;
import com.ucd.keynote.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private AuthService authService;
    private CombinedService combinedService;

    // 로그인 한 회원정보 불러오기
    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getCurrentUser() {
        UserResponseDTO userResponse = userService.getCurrentUser();

        ApiResponseDTO<UserResponseDTO> response = ApiResponseDTO.<UserResponseDTO>builder()
                .code(200)
                .message("success")
                .data(userResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<NoDataApiResponseDTO> joinProcess(@RequestBody SignUpRequestDTO joinDTO) {

        userService.userSign(joinDTO);

        // 회원가입 성공 응답
        NoDataApiResponseDTO response = NoDataApiResponseDTO.builder()
                .code(200)
                .message("success signuUp")
                .build();


        return ResponseEntity.ok(response);
    }

    @GetMapping("/home")
    public ResponseEntity<ApiResponseDTO<CombinedResponseDTO>> getUserOrganizationAndChannel(){
        // 현재 인증된 사용자 정보 가져오기
        Long userId = authService.getAuthenticatedUser().getUserId();

        // 통합 응답 생성
        CombinedResponseDTO combinedResponse = combinedService.getUserOrganizationsAndChannels(userId);

        // API 응답 객체 생성
        ApiResponseDTO<CombinedResponseDTO> response = ApiResponseDTO.<CombinedResponseDTO>builder()
                .code(200)
                .message("success")
                .data(combinedResponse)
                .build();

        // 응답 반환
        return ResponseEntity.ok(response);
    }

}
