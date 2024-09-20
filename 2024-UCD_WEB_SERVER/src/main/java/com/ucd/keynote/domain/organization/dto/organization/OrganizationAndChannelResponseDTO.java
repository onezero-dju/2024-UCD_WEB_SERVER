package com.ucd.keynote.domain.organization.dto.organization;

import com.ucd.keynote.domain.channel.dto.ChannelResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
public class OrganizationAndChannelResponseDTO {
    private Long organizationId;
    private String organizationName;
    private String description;
    private List<ChannelResponseDTO> channels;
}
