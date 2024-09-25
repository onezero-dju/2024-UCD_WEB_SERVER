package com.ucd.keynote.common.dto;

import com.ucd.keynote.domain.organization.dto.organization.OrganizationAndChannelResponseDTO;
import com.ucd.keynote.domain.user.dto.UserResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CombinedResponseDTO {
    private final UserResponseDTO user;
    private List<OrganizationAndChannelResponseDTO> organizations;
}
