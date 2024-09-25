package com.ucd.keynote.domain.user.controller;

import com.ucd.keynote.common.dto.ApiResponseDTO;
import com.ucd.keynote.common.dto.CombinedResponseDTO;
import com.ucd.keynote.common.dto.NoDataApiResponseDTO;
import com.ucd.keynote.common.service.AuthService;
import com.ucd.keynote.common.service.CombinedService;
import com.ucd.keynote.domain.user.dto.SignUpRequestDTO;
import com.ucd.keynote.domain.user.dto.UserResponseDTO;
import com.ucd.keynote.domain.user.dto.UsernameUpdateRequestDTO;
import com.ucd.keynote.domain.user.dto.UsernameUpdateResponseDTO;
import com.ucd.keynote.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
        Long userId = authService.getAuthenticatedUser().getUserId();

        UserResponseDTO userResponse = userService.getUserById(userId);

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
                .message("success signup")
                .build();


        return ResponseEntity.ok(response);
    }

    // 홈화면 정보 가져오기
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

    // 사용자 이름 변경
    @PutMapping("/me/username")
    public ResponseEntity<ApiResponseDTO<UsernameUpdateResponseDTO>> updateUsername(@RequestBody UsernameUpdateRequestDTO request){

        // 인증된 사용자 이름 변경 서비스 호출
        UsernameUpdateResponseDTO updateUser = userService.updateUsername(request.getNewUsername());

        // 응답 생성
        ApiResponseDTO<UsernameUpdateResponseDTO> response = ApiResponseDTO.<UsernameUpdateResponseDTO>builder()
                .code(200)
                .message("Username updated successfully")
                .data(updateUser)
                .build();
        return ResponseEntity.ok(response);

    }



}
