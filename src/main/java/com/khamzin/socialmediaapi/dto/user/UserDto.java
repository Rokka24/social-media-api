package com.khamzin.socialmediaapi.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.khamzin.socialmediaapi.dto.post.PostDto;
import com.khamzin.socialmediaapi.dto.request.RequestDto;
import com.khamzin.socialmediaapi.model.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {

    @NotBlank(message = "Username should not be empty")
    @Size(min = 2, max = 64, message = "Username should have 2 to 64 symbols")
    private String username;

    @NotBlank(message = "Enter a password")
    @Size(min = 8, message = "Password has to be at least 8 symbols")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private UserRole role;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ToString.Exclude
    private List<UserCollectionDto> friends;
    @ToString.Exclude
    private List<UserCollectionDto> subscribers;
    @ToString.Exclude
    private List<PostDto> posts;
    @ToString.Exclude
    private List<RequestDto> requests;
}
