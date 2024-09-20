package com.ucd.keynote.domain.organization.controller;

import com.ucd.keynote.domain.common.dto.ApiResponseDTO;
import com.ucd.keynote.domain.organization.dto.join.JoinRequestDTO;
import com.ucd.keynote.domain.organization.dto.join.JoinRequestResponseDTO;
import com.ucd.keynote.domain.organization.service.OrganizationJoinService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizations")
@AllArgsConstructor
public class OrganizationJoinController {
    private final OrganizationJoinService organizationJoinService;

    // 가입신청
    @PostMapping("/{organizationId}/join")
    public ResponseEntity<ApiResponseDTO<JoinRequestResponseDTO>> requestJoinOrganization(@PathVariable Long organizationId,
                                                                        @RequestBody JoinRequestDTO request){
        JoinRequestResponseDTO joinRequestResponse = organizationJoinService.requestJoinOrganization(organizationId, request);

        ApiResponseDTO<JoinRequestResponseDTO> response = ApiResponseDTO.<JoinRequestResponseDTO>builder()
                .code(201)
                .message("가입 신청이 완료되었습니다. 관리자의 승인을 기다려주세요.")
                .data(joinRequestResponse)
                .build();
        return ResponseEntity.status(201).body(response);
    }
}
