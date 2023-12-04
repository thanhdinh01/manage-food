package demo.internspring1.controller.authentication;

import demo.internspring1.dto.request.PasswordRequest;
import demo.internspring1.service.impl.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/v1/auth/")
public class PasswordController {
    @Autowired
    private PasswordService passwordService;
    @PostMapping("/password-reset-request")
    @ResponseBody
    public String resetPasswordRequest(@RequestBody PasswordRequest passwordRequest){
        String result= passwordService.passwordResetRequest(passwordRequest.getEmail());
        return result;
    }

    @PostMapping("/reset-password")
    @ResponseBody
    public String resetPasswordVerification(@RequestBody PasswordRequest passwordRequest, @RequestParam("token") String token){
        System.out.println(token);
        String result= passwordService.passwordResetVerification(passwordRequest, token);
        return result;
    }
}
