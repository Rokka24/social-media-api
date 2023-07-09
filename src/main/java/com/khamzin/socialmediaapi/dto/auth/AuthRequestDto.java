package com.khamzin.socialmediaapi.dto.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthRequestDto {
    @NotBlank(message = "Username should not be empty")
    private String username;
    @NotBlank(message = "Enter a password")
    private String password;
}
