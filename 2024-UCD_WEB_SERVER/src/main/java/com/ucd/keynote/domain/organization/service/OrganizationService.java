package com.ucd.keynote.domain.organization.service;


import com.ucd.keynote.common.dto.ApiResponseDTO;
import com.ucd.keynote.domain.organization.dto.organization.OrganizationResponseDTO;
import com.ucd.keynote.domain.organization.dto.organizationUser.OrganizationUserDTO;
import com.ucd.keynote.domain.organization.dto.organizationUser.UserOrganizationDTO;
import com.ucd.keynote.domain.organization.entity.Organization;
import com.ucd.keynote.domain.organization.entity.UserOrganization;
import com.ucd.keynote.domain.organization.entity.UserOrganizationId;
import com.ucd.keynote.domain.organization.repository.OrganizationRepository;
import com.ucd.keynote.domain.organization.repository.UserOrganizationRepository;
import com.ucd.keynote.domain.user.dto.CustomUserDetails;
import com.ucd.keynote.domain.user.entity.UserEntity;
import com.ucd.keynote.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final UserOrganizationRepository userOrganizationRepository;
    private final UserRepository userRepository;


    // 조직 생성 서비스
    public ApiResponseDTO<OrganizationResponseDTO> createOrganization(String organizationName, String description, Long userId) {
        // 조직 이름이 중복되지 않도록 검사
        if (organizationRepository.existsByOrganizationName(organizationName)) {
            throw new IllegalArgumentException("Organization name already exists");
        }

        // 새로운 조직 생성
        Organization organization = new Organization();
        organization.setOrganizationName(organizationName);
        organization.setDescription(description);
        organization.setCreatedAt(LocalDateTime.now());
        organization.setUpdatedAt(LocalDateTime.now());

        organizationRepository.save(organization);

        // 사용자 엔티티 조회
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // 사용자와 조직 간의 관계 설정 (관리자 역할)
        UserOrganizationId id = new UserOrganizationId();
        id.setUserId(userId);
        id.setOrganizationId(organization.getOrganizationId());

        UserOrganization userOrganization = new UserOrganization();
        userOrganization.setId(id);
        userOrganization.setRole("admin");
        userOrganization.setJoinedAt(LocalDateTime.now());
        userOrganization.setOrganization(organization);
        userOrganization.setUser(userEntity);

        userOrganizationRepository.save(userOrganization);

        // OrganizationResponseDTO 생성
        OrganizationResponseDTO organizationResponseDTO = OrganizationResponseDTO.builder()
                .organizationId(organization.getOrganizationId())
                .organizationName(organization.getOrganizationName())
                .description(organization.getDescription())
                .createdAt(organization.getCreatedAt())
                .build();

        // ApiResponseDTO 생성
        return ApiResponseDTO.<OrganizationResponseDTO>builder()
                .code(201)  // HTTP 상태 코드
                .message("Organization created successfully")
                .data(organizationResponseDTO)
                .build();
    }

    //사용자 속한 조직 정보 가져오기
    public List<UserOrganizationDTO> getUserOrganization() {
        // 현재 인증된 사용자 정보 받아오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 로그인 된 사용자 아이디
        Long userId = userDetails.getUserEntity().getUserId();

        // 사용자가 속한 조직 정보 가져오기
        List<UserOrganization> userOrganizations = userOrganizationRepository.findByUser_UserId(userId);

        return userOrganizations.stream()
                .map(userOrg -> UserOrganizationDTO.builder()
                        .organizationId(userOrg.getOrganization().getOrganizationId())
                        .organizationName(userOrg.getOrganization().getOrganizationName())
                        .description(userOrg.getOrganization().getDescription())
                        .role(userOrg.getRole())
                        .joinedAt(userOrg.getJoinedAt())
                        .build())
                .collect(Collectors.toList());

    }

    // 조직 멤버 정보 가져오기
    public List<OrganizationUserDTO> getUsersByOrganizationId(Long organizationId){
        List<UserOrganization> userOrganizations = userOrganizationRepository.findByOrganization_OrganizationId(organizationId);

        return userOrganizations.stream()
                .map(userOrganization -> OrganizationUserDTO.builder()
                        .userId(userOrganization.getUser().getUserId())
                        .userName(userOrganization.getUser().getUsername())
                        .email(userOrganization.getUser().getEmail())
                        .role(userOrganization.getRole())
                        .joinedAt(userOrganization.getJoinedAt())
                        .build()
                ).collect(Collectors.toList());
    }

    // 조직 검색 서비스
    public List<OrganizationResponseDTO> searchOrganization(String keyword, int page, int size){
        PageRequest pageable = PageRequest.of(page, size);

        // 조직 이름에 keyword가 포함된 결과 검색
        List<Organization> organizations = organizationRepository.findByOrganizationNameContaining(keyword, pageable);
        System.out.println("검색된 조직: " + organizations.size());

        // 검색 결과 DTO로 변환하여 반환
        return organizations.stream()
                .map(org -> OrganizationResponseDTO.builder()
                        .organizationId(org.getOrganizationId())
                        .organizationName(org.getOrganizationName())
                        .description(org.getDescription())
                        .createdAt(org.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

}
