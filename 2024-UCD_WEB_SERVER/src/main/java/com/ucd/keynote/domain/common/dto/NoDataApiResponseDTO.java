package com.ucd.keynote.domain.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoDataApiResponseDTO {
    private int code;
    private String message;
}
