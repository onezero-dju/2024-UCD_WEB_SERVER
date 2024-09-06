package com.ucd.keynote.domain.channel.service;

import com.ucd.keynote.domain.channel.dto.ChannelRequestDTO;
import com.ucd.keynote.domain.channel.dto.ChannelResponseDTO;
import com.ucd.keynote.domain.channel.entity.Channel;
import com.ucd.keynote.domain.channel.repository.ChannelRepository;
import com.ucd.keynote.domain.organization.entity.Organization;
import com.ucd.keynote.domain.organization.entity.UserOrganization;
import com.ucd.keynote.domain.organization.repository.OrganizationRepository;
import com.ucd.keynote.domain.organization.repository.UserOrganizationRepository;
import com.ucd.keynote.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.nio.channels.AcceptPendingException;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final OrganizationRepository organizationRepository;
    private final UserOrganizationRepository userOrganizationRepository;
    private final UserService userService;

    // 채널 생성
    public ChannelResponseDTO createdChannel(ChannelRequestDTO request, Long organizationId, Long userId){
        // 조직 존재 여부 확인
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new IllegalArgumentException("조직이 존재 하지 않습니다."));

        // 사용자가 조직에 속해 있는지 확인하고, 관리자 권한이 있는지 확인
        UserOrganization userOrganization = userOrganizationRepository.findByOrganization_OrganizationIdAndUser_UserId(organizationId, userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 조직에 속해 있지 않습니다."));

        if (!"admin".equals(userOrganization.getRole())) {
            throw new AccessDeniedException("admin 권한을 가진 사람만 채널 생성이 가능합니다.");
        }

        // 새로운 채널 생성
        Channel channel = new Channel();
        channel.setName(request.getName());
        channel.setDescription(request.getDescription());
        channel.setOrganization(organization);
        channel.setCreatedAt(LocalDateTime.now());
        channel.setUpdateAt(LocalDateTime.now());

        // 채널 저장
        channelRepository.save(channel);

        // 응답 객체 생성 및 반환
        ChannelResponseDTO response = ChannelResponseDTO.builder()
                .channelId(channel.getChannelId())
                .name(channel.getName())
                .description(channel.getDescription())
                .createdAt(channel.getCreatedAt())
                .build();

        return response;
    }

    // 조직안 채널 정보 조회
    public List<ChannelResponseDTO> getChannelByOrganizationId(Long organizationId){
        List<Channel> channels = channelRepository.findByOrganization_OrganizationId(organizationId);

        return channels.stream()
                .map(channel -> ChannelResponseDTO.builder()
                        .channelId(channel.getChannelId())
                        .name(channel.getName())
                        .description(channel.getDescription())
                        .createdAt(channel.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

}
