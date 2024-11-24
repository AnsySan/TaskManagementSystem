package com.ansysan.task_management_system.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class JwtResponseDto {
    @NonNull
    private String accessToken;
    private String tokenType = "Bearer";
}
