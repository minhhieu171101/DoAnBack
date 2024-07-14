package com.example.comic_store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String tokenName;

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
