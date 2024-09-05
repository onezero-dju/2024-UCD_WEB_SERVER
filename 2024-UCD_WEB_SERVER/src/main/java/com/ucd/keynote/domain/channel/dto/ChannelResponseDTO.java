package com.ucd.keynote.domain.channel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChannelResponseDTO {
    private Long channelId;
    private String name;
    private String description;
    private Long organizationId;
    private String createdAt;
}
