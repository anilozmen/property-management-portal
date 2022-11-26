package edu.miu.propertymanagement.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    public String accessToken;
    public String refreshToken;
    public String userType;
    public String fullName;
    public String email;
}
