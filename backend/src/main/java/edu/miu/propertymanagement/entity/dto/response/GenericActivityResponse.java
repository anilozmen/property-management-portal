package edu.miu.propertymanagement.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericActivityResponse {
    public boolean success;
    public String message;
}
