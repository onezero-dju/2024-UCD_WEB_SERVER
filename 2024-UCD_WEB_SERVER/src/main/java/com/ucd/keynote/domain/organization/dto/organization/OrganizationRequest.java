package com.ucd.keynote.domain.organization.dto.organization;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationRequest {
    private String organizationName;
    private String description;
}