package com.khamzin.socialmediaapi.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.khamzin.socialmediaapi.model.request.Status;
import com.khamzin.socialmediaapi.model.request.Type;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestDto {

    private Type type;
    private Status status;
    @NotNull(message = "recipient_id can not be null")
    private Long recipientId;
    @NotNull(message = "sender_id can not be null")
    private Long senderId;
}
