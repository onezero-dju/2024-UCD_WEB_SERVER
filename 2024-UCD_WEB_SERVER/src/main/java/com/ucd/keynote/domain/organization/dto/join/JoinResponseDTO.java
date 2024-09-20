package com.ucd.keynote.domain.organization.dto.join;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class JoinResponseDTO {
    private Long requestId;
    private Long organizationId;
    private String status;
    private LocalDateTime createdAt;
}
