package edu.miu.propertymanagement.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ErrorResponse {
    public ErrorDto error;
    
    public ErrorResponse(String message) {
        this.error = new ErrorDto(message);
    }
}
