package edu.miu.propertymanagement.service;

public interface EmailService {
    void send(String to, String subject, String body);

    void sendWithHTMLBody(String to, String subject, String body);
}
