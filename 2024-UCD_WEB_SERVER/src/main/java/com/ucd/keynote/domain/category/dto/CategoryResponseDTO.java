package com.ucd.keynote.domain.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonPropertyOrder({"category_id", "name", "channel_id", "created_at"})
public class CategoryResponseDTO {
    @JsonProperty("category_id")
    private Long categoryId;
    private String name;
    @JsonProperty("channel_id")
    private Long channelId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
