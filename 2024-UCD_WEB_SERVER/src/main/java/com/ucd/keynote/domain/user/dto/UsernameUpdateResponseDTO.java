package com.ucd.keynote.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsernameUpdateResponseDTO {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("new_username")
    private String newUsername;
}
