package com.comenrce_eletronico.frontend.dto;

public record LoginResponse(
        String token,
        String message,
        Long userId,
        String name,
        String email,
        String role
) {
}

