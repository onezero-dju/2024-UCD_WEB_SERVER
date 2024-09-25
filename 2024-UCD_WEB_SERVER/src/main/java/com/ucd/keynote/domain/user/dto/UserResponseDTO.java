package com.ucd.keynote.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("username")
    private String userName;
    private String email;
    private String role;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
