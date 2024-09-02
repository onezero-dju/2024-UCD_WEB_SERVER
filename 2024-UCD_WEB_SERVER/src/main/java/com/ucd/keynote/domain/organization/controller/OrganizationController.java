package com.ucd.keynote.domain.organization.controller;

import com.ucd.keynote.domain.organization.dto.OrganizationRequest;
import com.ucd.keynote.domain.organization.entity.Organization;
import com.ucd.keynote.domain.organization.service.OrganizationService;
import com.ucd.keynote.domain.user.dto.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    @PostMapping
    public ResponseEntity<?> createOrganization(OrganizationRequest request, Authentication authentication) {
        // 인증된 사용자 정보 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserEntity().getUserId();

        // 서비스 계층에서 조직 생성 처리
        Organization organization = organizationService.createOrganization(request.getOrganizationName(), request.getDescription(), userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(organization);
    }
}
