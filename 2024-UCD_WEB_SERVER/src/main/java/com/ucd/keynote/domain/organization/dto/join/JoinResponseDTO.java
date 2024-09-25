package com.ucd.keynote.domain.organization.dto.join;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class JoinResponseDTO {
    @JsonProperty("request_id")
    private Long requestId;
    @JsonProperty("organization_id")
    private Long organizationId;
    private String status;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
