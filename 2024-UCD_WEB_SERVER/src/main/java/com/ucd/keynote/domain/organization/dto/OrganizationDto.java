package com.ucd.keynote.domain.organization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationDto {
    private Long organizationId;
    private String name;
    private String description;
}
