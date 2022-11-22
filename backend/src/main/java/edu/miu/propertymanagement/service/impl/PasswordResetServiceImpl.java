package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.PasswordResetToken;
import edu.miu.propertymanagement.repository.PasswordResetRepository;
import edu.miu.propertymanagement.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private PasswordResetRepository passwordResetRepository;


    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passwordResetToken = passwordResetRepository.findByToken(token);

        return !isTokenFound(passwordResetToken) ? "invalidToken"
                : isTokenExpired(passwordResetToken) ? "expired"
                : null;
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetRepository.findByToken(token);
    }

    @Override
    public boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    @Override
    public boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
