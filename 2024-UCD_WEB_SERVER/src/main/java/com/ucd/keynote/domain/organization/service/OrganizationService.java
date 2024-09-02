package com.ucd.keynote.domain.organization.service;


import com.ucd.keynote.domain.organization.entity.Organization;
import com.ucd.keynote.domain.organization.entity.UserOrganization;
import com.ucd.keynote.domain.organization.entity.UserOrganizationId;
import com.ucd.keynote.domain.organization.repository.OrganizationRepository;
import com.ucd.keynote.domain.organization.repository.UserOrganizationRepository;
import com.ucd.keynote.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final UserOrganizationRepository userOrganizationRepository;
    private final UserRepository userRepository;

    // 모든 repository를 주입받도록 수정
    public OrganizationService(OrganizationRepository organizationRepository, UserOrganizationRepository userOrganizationRepository, UserRepository userRepository) {
        this.organizationRepository = organizationRepository;
        this.userOrganizationRepository = userOrganizationRepository;
        this.userRepository = userRepository;
    }

    public Organization createOrganization(String organizationName, String description, Long userId) {
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

        // 사용자와 조직 간의 관계 설정 (관리자 역할)
        UserOrganizationId id = new UserOrganizationId(userId, organization.getOrganizationId());

        UserOrganization userOrganization = new UserOrganization();
        userOrganization.setId(id);
        userOrganization.setRole("admin");
        userOrganization.setJoinedAt(LocalDateTime.now());

        userOrganizationRepository.save(userOrganization);

        return organization;
    }
}
