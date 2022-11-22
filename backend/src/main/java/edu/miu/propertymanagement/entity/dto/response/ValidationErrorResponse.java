package edu.miu.propertymanagement.entity.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    public ValidationErrorListDto error;

    public ValidationErrorResponse(List<String> errorMessages) {
        error = new ValidationErrorListDto(errorMessages);
    }
}
