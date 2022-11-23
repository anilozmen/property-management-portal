package edu.miu.propertymanagement.entity.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetResponse {
    private String token;
}
