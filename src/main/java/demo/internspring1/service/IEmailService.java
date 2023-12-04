package demo.internspring1.service;


import jakarta.mail.MessagingException;


public interface IEmailService {
    public void sendOtpEmail(String email, String subject, String otp, String contentHtml) throws MessagingException;
}
