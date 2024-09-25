package com.ucd.keynote.domain.organization.dto.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonPropertyOrder({ "organization_id", "organization_name", "description", "created_at" })
public class OrganizationResponseDTO {
    @JsonProperty("organization_id")
    private Long organizationId;
    @JsonProperty("organization_name")
    private String organizationName;
    private String description;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
