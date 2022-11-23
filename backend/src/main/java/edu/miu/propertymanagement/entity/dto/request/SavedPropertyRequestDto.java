package edu.miu.propertymanagement.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedPropertyRequestDto {
    @NotNull(message = "Property Id is required")
    long propertyId;
}
