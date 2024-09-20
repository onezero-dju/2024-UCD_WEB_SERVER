package com.ucd.keynote.domain.organization.controller;

import com.ucd.keynote.domain.common.dto.ApiResponseDTO;
import com.ucd.keynote.domain.organization.dto.join.JoinRequestDTO;
import com.ucd.keynote.domain.organization.dto.join.JoinRequestResponseDTO;
import com.ucd.keynote.domain.organization.dto.join.JoinResponseDTO;
import com.ucd.keynote.domain.organization.service.OrganizationJoinService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@AllArgsConstructor
public class OrganizationJoinController {
    private final OrganizationJoinService organizationJoinService;

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
}
