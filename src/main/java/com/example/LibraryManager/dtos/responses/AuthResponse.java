package com.example.LibraryManager.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthResponse {
    @JsonProperty("access_token")
    String accessToken;

    @JsonProperty("refresh_token")
    String refreshToken;
}
