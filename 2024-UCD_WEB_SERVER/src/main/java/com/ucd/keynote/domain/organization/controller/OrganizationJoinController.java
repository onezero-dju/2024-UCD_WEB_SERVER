package com.ucd.keynote.domain.organization.controller;

import com.ucd.keynote.domain.common.dto.ApiResponseDTO;
import com.ucd.keynote.domain.common.service.AuthService;
import com.ucd.keynote.domain.organization.dto.join.JoinRequestDTO;
import com.ucd.keynote.domain.organization.dto.join.JoinRequestResponseDTO;
import com.ucd.keynote.domain.organization.dto.join.JoinResponseDTO;
import com.ucd.keynote.domain.organization.entity.UserOrganization;
import com.ucd.keynote.domain.organization.repository.UserOrganizationRepository;
import com.ucd.keynote.domain.organization.service.OrganizationJoinService;
import com.ucd.keynote.domain.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@AllArgsConstructor
public class OrganizationJoinController {
    private final OrganizationJoinService organizationJoinService;
    private final AuthService authService;
    private final UserOrganizationRepository userOrganizationRepository;

    // 가입신청
    @PostMapping("/{organizationId}/join")
    public ResponseEntity<ApiResponseDTO<JoinResponseDTO>> requestJoinOrganization(@PathVariable Long organizationId,
                                                                                   @RequestBody JoinRequestDTO request){
        JoinResponseDTO joinRequestResponse = organizationJoinService.requestJoinOrganization(organizationId, request);

        ApiResponseDTO<JoinResponseDTO> response = ApiResponseDTO.<JoinResponseDTO>builder()
                .code(201)
                .message("가입 신청이 완료되었습니다. 관리자의 승인을 기다려주세요.")
                .data(joinRequestResponse)
                .build();
        return ResponseEntity.status(201).body(response);
    }

    // 가입 신청  목록 조회
    @GetMapping("/{organizationId}/join-requests")
    public ResponseEntity<ApiResponseDTO<List<JoinRequestResponseDTO>>> getJoinRequests(@PathVariable Long organizationId){
        List<JoinRequestResponseDTO> joinRequests = organizationJoinService.getJoinRequests(organizationId);
        ApiResponseDTO<List<JoinRequestResponseDTO>> response = ApiResponseDTO.<List<JoinRequestResponseDTO>>builder()
                .code(200)
                .message("success")
                .data(joinRequests)
                .build();
        return ResponseEntity.ok(response);
    }

    // 가입 요청 승인
    @PostMapping("/{organizationId}/join-requests/{requestId}/approve")
    public ResponseEntity<ApiResponseDTO<Void>> approveJoinRequest(@PathVariable Long organizationId,
                                                                   @PathVariable Long requestId){
        System.out.println("orID" + organizationId + "reId" + requestId);

        // 사용자 권한 확인
        UserEntity userEntity = authService.getAuthenticatedUser();
        // 해당 조직에 admin 권한이 있는지 확인
        UserOrganization userOrganization = userOrganizationRepository.findByOrganization_OrganizationIdAndUser_UserId(organizationId, userEntity.getUserId())
                .orElseThrow(() -> new AccessDeniedException("이 조직에서 권한이 없습니다."));
        //admin 권한 체크
        if (!"admin".equals(userOrganization.getRole())) {
            throw new AccessDeniedException("admin 권한이 있어야 요청을 승인할 수 있습니다.");
        }

        // 가입 요청 승인 처리
        organizationJoinService.approveJoniRequest(organizationId, requestId);

        // 응답 생성
        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .code(200)
                .message("가입 요청이 승인이되었습니다.")
                .build();

        return ResponseEntity.ok(response);
    }
}
