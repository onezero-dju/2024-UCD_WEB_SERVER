package com.ucd.keynote.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoDataApiResponseDTO {
    private int code;
    private String message;
}
