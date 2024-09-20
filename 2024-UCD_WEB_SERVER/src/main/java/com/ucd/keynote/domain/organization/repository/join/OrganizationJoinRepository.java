package com.ucd.keynote.domain.organization.repository.join;

import com.ucd.keynote.domain.organization.entity.join.OrganizationJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationJoinRepository extends JpaRepository<OrganizationJoinRequest, Long> {
    // 조직 가입 요청 목록 조회
    List<OrganizationJoinRequest> findByOrganization_OrganizationIdAndStatus(Long organizationId, String status);

    // 특정 가입 요청이 해당 조직에 속하는지 확인
    Optional<OrganizationJoinRequest> findByIdAndOrganization_OrganizationId(Long requestId, Long organizationId);

    // 가입 신청 중복 확인
    boolean existsByOrganization_OrganizationIdAndUser_UserIdAndStatus(Long organizationId, Long userId, String status);
}

