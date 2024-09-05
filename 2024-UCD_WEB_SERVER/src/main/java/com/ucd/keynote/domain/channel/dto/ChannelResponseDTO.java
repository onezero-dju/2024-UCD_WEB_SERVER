package com.ucd.keynote.domain.channel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChannelResponseDTO {
    private Long channelId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
