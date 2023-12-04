package demo.internspring1.service;

import demo.internspring1.dto.request.PasswordRequest;

public interface IPasswordService {
    public String passwordResetRequest(String email);
    public String passwordResetVerification(PasswordRequest passwordRequest, String token);
}
