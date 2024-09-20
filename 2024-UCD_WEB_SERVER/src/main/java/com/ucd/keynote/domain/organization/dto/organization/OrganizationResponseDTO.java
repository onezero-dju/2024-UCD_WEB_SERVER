package com.ucd.keynote.domain.organization.dto.organization;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrganizationResponseDTO {
    private Long organizationId;
    private String organizationName;
    private String description;
    private LocalDateTime createdAt;
}
