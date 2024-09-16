package com.ucd.keynote.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameUpdateRequestDTO {
    @JsonProperty("new_username")
    private String newUsername;
}
