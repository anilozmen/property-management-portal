package edu.miu.propertymanagement.service;

import edu.miu.propertymanagement.entity.PasswordResetToken;

public interface PasswordResetService {

    String validatePasswordResetToken(String token);

    PasswordResetToken findByToken(String token);

    boolean isTokenFound(PasswordResetToken passToken);

    boolean isTokenExpired(PasswordResetToken passToken);
}
