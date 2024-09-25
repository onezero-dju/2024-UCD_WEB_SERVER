package com.ucd.keynote.domain.organization.dto.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ucd.keynote.domain.channel.dto.ChannelResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
@JsonPropertyOrder({ "organization_id", "organization_name", "description", "channels" })
public class OrganizationAndChannelResponseDTO {
    @JsonProperty("organization_id")
    private Long organizationId;
    @JsonProperty("organization_name")
    private String organizationName;
    private String description;
    private List<ChannelResponseDTO> channels;
}
