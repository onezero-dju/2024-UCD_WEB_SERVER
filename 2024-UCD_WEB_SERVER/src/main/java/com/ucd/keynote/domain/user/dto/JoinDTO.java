package com.ucd.keynote.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDTO {
    private String name;
    private String email;
    private String password;
}
