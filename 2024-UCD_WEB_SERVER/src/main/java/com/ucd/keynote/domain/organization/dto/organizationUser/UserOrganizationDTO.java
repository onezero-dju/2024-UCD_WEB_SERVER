package com.ucd.keynote.domain.organization.dto.organizationUser;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class UserOrganizationDTO {
    private Long organizationId;
    private String organizationName;
    private String description;
    private String role;
    private LocalDateTime joinedAt;
}
