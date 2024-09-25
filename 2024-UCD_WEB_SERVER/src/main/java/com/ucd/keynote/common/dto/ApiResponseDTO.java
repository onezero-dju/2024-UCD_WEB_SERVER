package com.ucd.keynote.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ApiResponseDTO<T> {
    private int code;
    private String message;
    private T data;
}
