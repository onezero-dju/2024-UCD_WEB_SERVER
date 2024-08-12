package com.ucd.keynote.domain.organization.repository;

import com.ucd.keynote.domain.organization.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {
}
