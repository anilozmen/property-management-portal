package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void send(String to, String subject, String body) {
        System.out.println(to);
        System.out.println(subject);
        System.out.println(body);
    }
}
