package com.ucd.keynote.domain.common.service;

import com.ucd.keynote.domain.channel.dto.ChannelResponseDTO;
import com.ucd.keynote.domain.channel.service.ChannelService;
import com.ucd.keynote.domain.common.dto.CombinedResponseDTO;
import com.ucd.keynote.domain.organization.dto.organization.OrganizationAndChannelResponseDTO;
import com.ucd.keynote.domain.organization.dto.UserOrganizationDTO;
import com.ucd.keynote.domain.organization.service.OrganizationService;
import com.ucd.keynote.domain.user.dto.UserResponseDTO;
import com.ucd.keynote.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CombinedService {
    private UserService userService;
    private OrganizationService organizationService;
    private ChannelService channelService;

    public CombinedResponseDTO getUserOrganizationsAndChannels(Long userId){
        // 사용자 정보 조회
        UserResponseDTO userResponse = userService.getUserById(userId);

        // 사용자가 속한 조직 목록 가져오기
        List<UserOrganizationDTO> userOrganizations = organizationService.getUserOrganization();

        // 각 조직에 속한 채널 정보 가져오기
        List<OrganizationAndChannelResponseDTO> organizationResponse = userOrganizations.stream()
                .map(userOrg -> {
                    // 조직 정보 가져오기
                    Long organizationId = userOrg.getOrganizationId();

                    // 해당 조직의 채널 정보 가져오기
                    List<ChannelResponseDTO> channelResponses = channelService.getChannelByOrganizationId(organizationId);

                    // 조직 정보와 채널 리스트를 포함한 DTO 생성
                    return OrganizationAndChannelResponseDTO.builder()
                            .organizationId(userOrg.getOrganizationId())
                            .organizationName(userOrg.getOrganizationName())
                            .description(userOrg.getDescription())
                            .channels(channelResponses)
                            .build();
                }).collect(Collectors.toList());

        // 최종 통합 응답 생성
        return CombinedResponseDTO.builder()
                .user(userResponse)
                .organizations(organizationResponse)
                .build();
    }
}
