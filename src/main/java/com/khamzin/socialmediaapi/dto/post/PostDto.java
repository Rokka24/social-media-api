package com.khamzin.socialmediaapi.dto.post;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostDto {

    private String author;
    @NotBlank(message = "Enter a header")
    @Size(max = 100, message = "The header must not contain more than 100 symbols")
    private String header;
    @NotBlank(message = "Enter a text")
    @Size(max = 500, message = "The text must not contain more than 500 symbols")
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private byte[] images;
}
