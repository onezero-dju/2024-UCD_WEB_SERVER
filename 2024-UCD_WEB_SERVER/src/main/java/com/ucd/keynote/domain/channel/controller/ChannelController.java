package com.ucd.keynote.domain.channel.controller;

import com.ucd.keynote.domain.channel.dto.ChannelRequestDTO;
import com.ucd.keynote.domain.channel.dto.ChannelResponseDTO;
import com.ucd.keynote.domain.channel.service.ChannelService;
import com.ucd.keynote.domain.common.dto.ApiResponseDTO;
import com.ucd.keynote.domain.common.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/organizations")
public class ChannelController {
    private final ChannelService channelService;
    private final AuthService authService;

    @PostMapping("/{organizationId}/channels")
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

}
