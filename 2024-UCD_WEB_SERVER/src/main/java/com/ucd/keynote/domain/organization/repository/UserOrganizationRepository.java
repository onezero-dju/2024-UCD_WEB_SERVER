package com.ucd.keynote.domain.organization.repository;

import com.ucd.keynote.domain.organization.entity.UserOrganization;
import com.ucd.keynote.domain.organization.entity.UserOrganizationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrganizationRepository extends JpaRepository<UserOrganization, UserOrganizationId> {
}
