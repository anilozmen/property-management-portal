package edu.miu.propertymanagement.entity.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerMessageRequest {
    @NotBlank(message = "reply field can't be empty")
    private String reply;
}
