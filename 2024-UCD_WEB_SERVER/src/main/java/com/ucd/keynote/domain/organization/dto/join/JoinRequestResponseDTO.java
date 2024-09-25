package com.ucd.keynote.domain.organization.dto.join;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class JoinRequestResponseDTO {
    @JsonProperty("request_id")
    private Long requestId;
    @JsonProperty("user_Id")
    private Long userId;
    @JsonProperty("username")
    private String userName;
    private String message;
    private String status;
    @JsonProperty("request_at")
    private LocalDateTime requestedAt;
}

