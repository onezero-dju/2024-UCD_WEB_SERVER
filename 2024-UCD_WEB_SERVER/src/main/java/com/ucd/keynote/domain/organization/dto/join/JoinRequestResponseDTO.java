package com.ucd.keynote.domain.organization.dto.join;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class JoinRequestResponseDTO {
    private Long requestId;
    private Long userId;
    private String userName;
    private String message;
    private String status;
    private LocalDateTime requestAt;
}
