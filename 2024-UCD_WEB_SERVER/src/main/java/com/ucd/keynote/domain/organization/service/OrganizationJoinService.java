package com.ucd.keynote.domain.organization.service;

import com.ucd.keynote.domain.common.service.AuthService;
import com.ucd.keynote.domain.organization.dto.join.JoinRequestDTO;
import com.ucd.keynote.domain.organization.dto.join.JoinRequestResponseDTO;
import com.ucd.keynote.domain.organization.dto.join.JoinResponseDTO;
import com.ucd.keynote.domain.organization.entity.Organization;
import com.ucd.keynote.domain.organization.entity.join.OrganizationJoinRequest;
import com.ucd.keynote.domain.organization.repository.OrganizationRepository;
import com.ucd.keynote.domain.organization.repository.join.OrganizationJoinRepository;
import com.ucd.keynote.domain.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrganizationJoinService {
    private final OrganizationJoinRepository organizationJoinRepository;
    private final OrganizationRepository organizationRepository;
    private final AuthService authService;
    // 가입 신청 처리
    public JoinResponseDTO requestJoinOrganization(Long organizationId, JoinRequestDTO reqeust){
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new IllegalArgumentException("조직이 존재하지 않습니다."));
        UserEntity user = authService.getAuthenticatedUser();
        // 이미 가입 신청이 존재하는지 확인(PENDING 상태)
        boolean alreadyRequested = organizationJoinRepository.existsByOrganization_OrganizationIdAndUser_UserIdAndStatus
                (organizationId, user.getUserId(), "PENDING");
        if (alreadyRequested){
            throw new IllegalArgumentException("이미 조직에 가입 신청을 보냈습니다.");
        }

        // 가입 신청 생성
        OrganizationJoinRequest joinRequest = new OrganizationJoinRequest();
        joinRequest.setOrganization(organization);
        joinRequest.setUser(user);
        joinRequest.setStatus("PENDING");
        joinRequest.setRequestMessage(reqeust.getMessage());
        joinRequest.setRequestedAt(LocalDateTime.now());

        organizationJoinRepository.save(joinRequest);

        // 웅답 DTO 생성
        return JoinResponseDTO.builder()
                .requestId(user.getUserId())
                .organizationId(organization.getOrganizationId())
                .status(joinRequest.getStatus())
                .createdAt(joinRequest.getRequestedAt())
                .build();
    }

    // 가입 신청 목록 조회
    public List<JoinRequestResponseDTO> getJoinRequests(Long organizationId){
        List<OrganizationJoinRequest> joinRequests = organizationJoinRepository.findByOrganization_OrganizationIdAndStatus
                (organizationId, "PENDING");
        return joinRequests.stream().map(request -> JoinRequestResponseDTO.builder()
                .requestId(request.getId())
                .userId(request.getUser().getUserId())
                .userName(request.getUser().getUsername())
                .message(request.getRequestMessage())
                .status(request.getStatus())
                .requestedAt(request.getRequestedAt())
                .build()).collect(Collectors.toList());
    }
}
