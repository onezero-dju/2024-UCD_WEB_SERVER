package com.ucd.keynote.domain.channel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonPropertyOrder({ "channel_id", "name", "description", "created_at", "updated_at" })
public class ChannelResponseDTO {
    @JsonProperty("channel_id")
    private Long channelId;
    private String name;
    private String description;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
