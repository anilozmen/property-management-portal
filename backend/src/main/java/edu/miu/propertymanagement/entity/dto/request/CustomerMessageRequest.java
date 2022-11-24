package edu.miu.propertymanagement.entity.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMessageRequest {
    @NotBlank(message = "Message field cant be empty")
    private String message;
    
    @NotNull
    private long propertyId;
}
