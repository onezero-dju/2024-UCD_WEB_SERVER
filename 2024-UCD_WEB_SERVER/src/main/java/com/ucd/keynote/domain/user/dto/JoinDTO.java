package com.ucd.keynote.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDTO {
    private String username;
    private String email;
    private String password;
}
