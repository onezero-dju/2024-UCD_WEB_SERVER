package com.ucd.keynote.domain.organization.dto.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationRequest {
    @JsonProperty("organization_name")
    private String organizationName;
    private String description;
}
