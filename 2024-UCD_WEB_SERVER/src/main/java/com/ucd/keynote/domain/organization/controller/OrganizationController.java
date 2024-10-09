package com.ucd.keynote.domain.organization.controller;

import com.ucd.keynote.common.dto.ApiResponseDTO;
import com.ucd.keynote.common.service.AuthService;
import com.ucd.keynote.domain.organization.dto.organization.OrganizationRequest;
import com.ucd.keynote.domain.organization.dto.organization.OrganizationResponseDTO;
import com.ucd.keynote.domain.organization.dto.organizationUser.OrganizationUserDTO;
import com.ucd.keynote.domain.organization.dto.organizationUser.UserOrganizationDTO;
import com.ucd.keynote.domain.organization.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final AuthService authService;


    @PostMapping
    public ResponseEntity<ApiResponseDTO<OrganizationResponseDTO>> createOrganization(@RequestBody OrganizationRequest request, Authentication authentication) {
        // 인증된 사용자 정보 가져오기
        Long userId = authService.getAuthenticatedUser().getUserId();

        // 서비스 계층에서 조직 생성 처리
        ApiResponseDTO<OrganizationResponseDTO> response = organizationService.createOrganization(request.getOrganizationName(), request.getDescription(), userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponseDTO<List<UserOrganizationDTO>>> getUserOrganizations(){
        List<UserOrganizationDTO> userOrganizations = organizationService.getUserOrganization();

        ApiResponseDTO<List<UserOrganizationDTO>> response = ApiResponseDTO.<List<UserOrganizationDTO>>builder()
                .code(200)
                .message("success")
                .data(userOrganizations)
                .build();

        return ResponseEntity.ok(response);
    }

    // 조직에 멤버 검색
    @GetMapping("/{organizationId}/users")
    public ResponseEntity<ApiResponseDTO<List<OrganizationUserDTO>>> getUsersByOrganization(@PathVariable Long organizationId) {
        List<OrganizationUserDTO> users = organizationService.getUsersByOrganizationId(organizationId);

        ApiResponseDTO<List<OrganizationUserDTO>> response = ApiResponseDTO.<List<OrganizationUserDTO>>builder()
                .code(200)
                .message("success")
                .data(users)
                .build();

        return ResponseEntity.ok(response);
    }

    // 조직 검색
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO<List<OrganizationResponseDTO>>> searchOrganization(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        // 서비스 호출하여 검색 결과 가져오기
        List<OrganizationResponseDTO> organizations = organizationService.searchOrganization(keyword, page, size);

        ApiResponseDTO<List<OrganizationResponseDTO>> response = ApiResponseDTO.<List<OrganizationResponseDTO>>builder()
                .code(200)
                .message("success")
                .data(organizations)
                .build();

        return ResponseEntity.ok(response);
    }
}
