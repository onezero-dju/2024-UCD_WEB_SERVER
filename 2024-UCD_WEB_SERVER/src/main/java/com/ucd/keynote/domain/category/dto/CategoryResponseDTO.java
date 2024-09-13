package com.ucd.keynote.domain.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CategoryResponseDTO {
    private Long categoryId;
    private String name;
    private Long channelId;
    private LocalDateTime createdAt;
}
