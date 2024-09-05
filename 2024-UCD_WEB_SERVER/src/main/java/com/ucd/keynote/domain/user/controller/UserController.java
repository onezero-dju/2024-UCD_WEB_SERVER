package com.ucd.keynote.domain.user.controller;

import com.ucd.keynote.domain.common.dto.ApiResponseDTO;
import com.ucd.keynote.domain.common.dto.NoDataApiResponseDTO;
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
}
