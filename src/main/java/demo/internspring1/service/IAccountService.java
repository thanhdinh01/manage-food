package demo.internspring1.service;

import demo.internspring1.dto.request.ClientRegisterRequest;
import demo.internspring1.dto.request.LoginRequest;

public interface IAccountService  {
    public String register(ClientRegisterRequest clientRequest);
    public String verifyAccount(String email, String otp);
    public String regenerateOtp(String email);
}
