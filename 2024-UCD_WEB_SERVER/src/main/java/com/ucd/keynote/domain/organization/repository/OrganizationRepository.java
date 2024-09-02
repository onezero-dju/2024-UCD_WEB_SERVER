package com.ucd.keynote.domain.organization.repository;

import com.ucd.keynote.domain.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    // 조직 이름이 이미 존재하는지 확인하는 메서드
    boolean existsByOrganizationName(String organizationName);
}
