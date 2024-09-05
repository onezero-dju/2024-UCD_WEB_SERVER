package com.ucd.keynote.domain.organization.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrganizationUserDTO {
    private Long userId;
    private String userName;
    private String email;
    private String role;
    private LocalDateTime joinedAt;
}
