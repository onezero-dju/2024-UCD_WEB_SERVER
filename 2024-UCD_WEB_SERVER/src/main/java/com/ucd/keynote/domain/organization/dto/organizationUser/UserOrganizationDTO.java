package com.ucd.keynote.domain.organization.dto.organizationUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
@JsonPropertyOrder({ "organization_id", "organization_name", "joined_at" })
public class UserOrganizationDTO {
    @JsonProperty("organization_id")
    private Long organizationId;
    @JsonProperty("organization_name")
    private String organizationName;
    private String description;
    private String role;
    @JsonProperty("joined_at")
    private LocalDateTime joinedAt;
}
