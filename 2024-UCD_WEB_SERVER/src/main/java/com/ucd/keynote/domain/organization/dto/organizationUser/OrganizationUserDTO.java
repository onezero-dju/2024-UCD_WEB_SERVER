package com.ucd.keynote.domain.organization.dto.organizationUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrganizationUserDTO {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("username")
    private String userName;
    private String email;
    private String role;
    @JsonProperty("joined_at")
    private LocalDateTime joinedAt;
}
