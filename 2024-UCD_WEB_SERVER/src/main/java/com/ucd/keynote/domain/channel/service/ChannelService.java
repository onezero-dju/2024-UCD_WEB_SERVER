package com.ucd.keynote.domain.channel.service;

import com.ucd.keynote.domain.channel.dto.ChannelUpdateRequestDTO;
import com.ucd.keynote.domain.channel.dto.ChannelRequestDTO;
import com.ucd.keynote.domain.channel.dto.ChannelResponseDTO;
import com.ucd.keynote.domain.channel.entity.Channel;
import com.ucd.keynote.domain.channel.repository.ChannelRepository;
import com.ucd.keynote.common.service.AuthService;
import com.ucd.keynote.domain.organization.entity.Organization;
import com.ucd.keynote.domain.organization.entity.UserOrganization;
import com.ucd.keynote.domain.organization.repository.OrganizationRepository;
import com.ucd.keynote.domain.organization.repository.UserOrganizationRepository;
import com.ucd.keynote.domain.user.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final OrganizationRepository organizationRepository;
    private final UserOrganizationRepository userOrganizationRepository;
    private final AuthService authService;


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
        channel.setUpdatedAt(LocalDateTime.now());

        // 채널 저장
        channelRepository.save(channel);

        // 응답 객체 생성 및 반환
        ChannelResponseDTO response = ChannelResponseDTO.builder()
                .channelId(channel.getChannelId())
                .name(channel.getName())
                .description(channel.getDescription())
                .createdAt(channel.getCreatedAt())
                .updatedAt(channel.getUpdatedAt())
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

    // 채널 정보 수정
    public ChannelResponseDTO updateChannelName(Long channelId, ChannelUpdateRequestDTO request){
        // 로그인 한 사용자 정보 가져오기
        UserEntity currentUser = authService.getAuthenticatedUser();

        // 채널 존재 여부 확인
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("채널을 찾을 수 없습니다."));

        // 사용자가 속한 조직에서의 역할 확인
        UserOrganization userOrganization = userOrganizationRepository
                .findByOrganization_OrganizationIdAndUser_UserId(channel.getOrganization().getOrganizationId(), currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not part of the organization"));

        // 관리자인지 확인(admin 권한을 가진 사용자만 채널 정보를 수정할 수 있음)
        if(!"admin".equals(userOrganization.getRole())){
            throw new AccessDeniedException("관리자만 채널 정보를 수정할 수 있습니다.");
        }
        // 조직 내에서 중복된 채널 이름 확인
        if (channelRepository.existsByNameAndOrganization_OrganizationId(request.getNewName(), channel.getOrganization().getOrganizationId())) {
            throw new IllegalArgumentException("해당 조직 내에서 같은 이름의 채널이 이미 존재합니다.");
        }



        // 채널 이름과 설명 변경
        channel.setName(request.getNewName());
        channel.setDescription(request.getNewDescription());
        channel.setUpdatedAt(LocalDateTime.now());

        // 변경 사항 저장
        channelRepository.save(channel);

        //응답 객체 생성
        return ChannelResponseDTO.builder()
                .channelId(channel.getChannelId())
                .name(channel.getName())
                .description(channel.getDescription())
                .createdAt(channel.getCreatedAt())
                .updatedAt(channel.getUpdatedAt())
                .build();
    }

    // 채널 삭제 메서드
    @Transactional
    public void deleteChannel(Long organizationId, Long channelId) {
        // 현재 로그인한 사용자 정보 가져오기
        UserEntity user = authService.getAuthenticatedUser();

        // 조직 내 admin 권한 확인
        UserOrganization userOrganization = userOrganizationRepository
                .findByOrganization_OrganizationIdAndUser_UserId(organizationId, user.getUserId())
                .orElseThrow(() -> new AccessDeniedException("이 조직에서 권한이 없습니다."));

        if (!"admin".equals(userOrganization.getRole())) {
            throw new AccessDeniedException("admin 권한이 있어야 채널을 삭제할 수 있습니다.");
        }

        // 채널 조회 및 삭제
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("채널을 찾을 수 없습니다."));

        channelRepository.delete(channel);
    }

}
