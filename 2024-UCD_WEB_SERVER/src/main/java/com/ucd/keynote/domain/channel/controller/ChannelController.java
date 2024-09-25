package com.ucd.keynote.domain.channel.controller;

import com.ucd.keynote.domain.channel.dto.ChannelUpdateRequestDTO;
import com.ucd.keynote.domain.channel.dto.ChannelRequestDTO;
import com.ucd.keynote.domain.channel.dto.ChannelResponseDTO;
import com.ucd.keynote.domain.channel.service.ChannelService;
import com.ucd.keynote.common.dto.ApiResponseDTO;
import com.ucd.keynote.common.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ChannelController {
    private final ChannelService channelService;
    private final AuthService authService;

    // 채널 생성
    @PostMapping("/organizations/{organizationId}/channels")
    public ResponseEntity<ApiResponseDTO<ChannelResponseDTO>> createChannel(@PathVariable Long organizationId,
                                                                            @RequestBody ChannelRequestDTO request){
        // 로그인된 사용자 정보 가져오기
        Long userId = authService.getAuthenticatedUser().getUserId();

        // 채널 생성 서비스 호출
        ChannelResponseDTO channelResponse = channelService.createdChannel(request, organizationId, userId);

        // 응답 객체 생성
        ApiResponseDTO<ChannelResponseDTO> response = ApiResponseDTO.<ChannelResponseDTO>builder()
                .code(201)
                .message("Channel created successfully")
                .data(channelResponse)
                .build();

        return ResponseEntity.status(201).body(response);
    }

    // 조직 안 채널 목록 조회
    @GetMapping("/organizations/{organizationId}/channels")
    public ResponseEntity<ApiResponseDTO<List<ChannelResponseDTO>>> getChannelsByOrganization (
            @PathVariable Long organizationId
    ){
        List<ChannelResponseDTO> channels = channelService.getChannelByOrganizationId(organizationId);

        ApiResponseDTO<List<ChannelResponseDTO>> response = ApiResponseDTO.<List<ChannelResponseDTO>>builder()
                .code(200)
                .message("success")
                .data(channels)
                .build();

        return ResponseEntity.ok(response);
    }

    // 채널 정보 수정
    @PutMapping("/channels/{channelId}")
    public ResponseEntity<ApiResponseDTO<ChannelResponseDTO>> updateChannel (
            @PathVariable Long channelId, @RequestBody ChannelUpdateRequestDTO request
    ){
        // 채널 이름 및 설명 수정 서비스 호출
        ChannelResponseDTO updatedChannel = channelService.updateChannelName(channelId, request);

        // 응답 객체 생성
        ApiResponseDTO<ChannelResponseDTO> response = ApiResponseDTO.<ChannelResponseDTO>builder()
                .code(200)
                .message("Channel updated Successfully")
                .data(updatedChannel)
                .build();

        return ResponseEntity.ok(response);
    }

}
