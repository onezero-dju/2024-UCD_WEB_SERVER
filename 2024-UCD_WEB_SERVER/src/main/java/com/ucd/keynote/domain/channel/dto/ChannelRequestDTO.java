package com.ucd.keynote.domain.channel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChannelRequestDTO {
    private String name;
    private String description;
}