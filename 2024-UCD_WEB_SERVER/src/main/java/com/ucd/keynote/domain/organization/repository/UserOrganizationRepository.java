package com.ucd.keynote.domain.organization.repository;

import com.ucd.keynote.domain.organization.entity.UserOrganization;
import com.ucd.keynote.domain.organization.entity.UserOrganizationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOrganizationRepository extends JpaRepository<UserOrganization, UserOrganizationId> {
    // 사용자가 속한 조직 정보 가져오기
    List<UserOrganization> findByUser_UserId(Long userId);

    // 조직 멤버 정보 가져오기
    List<UserOrganization> findByOrganization_OrganizationId(Long organizationId);

    // 조직 ID와 사용자 ID로 UserOrganization 조회
    Optional<UserOrganization> findByOrganization_OrganizationIdAndUser_UserId(Long organizationId, Long userId);

    // 사용자가 특정 조직에 속해 있는지 여부 확인
    boolean existsByOrganization_OrganizationIdAndUser_UserId(Long organizationId, Long userId);

    List<UserOrganization> findByOrganization_OrganizationIdAndRole(Long organizationId, String role);
}
