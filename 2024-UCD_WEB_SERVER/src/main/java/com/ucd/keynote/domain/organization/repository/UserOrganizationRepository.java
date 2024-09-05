package com.ucd.keynote.domain.organization.repository;

import com.ucd.keynote.domain.organization.entity.UserOrganization;
import com.ucd.keynote.domain.organization.entity.UserOrganizationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrganizationRepository extends JpaRepository<UserOrganization, UserOrganizationId> {
    List<UserOrganization> findByUser_UserId(Long userId);

    List<UserOrganization> findByOrganization_OrganizationId(Long organizationId);
}
