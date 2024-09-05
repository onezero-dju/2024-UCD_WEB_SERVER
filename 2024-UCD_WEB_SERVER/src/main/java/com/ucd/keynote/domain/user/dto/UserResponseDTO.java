package com.ucd.keynote.domain.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private String userName;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}
