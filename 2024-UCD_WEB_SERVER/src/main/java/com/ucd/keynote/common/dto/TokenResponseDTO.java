package com.ucd.keynote.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseDTO {
    private int code;
    private String message;
    private String token;
}
