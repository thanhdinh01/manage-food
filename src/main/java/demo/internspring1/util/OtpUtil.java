package demo.internspring1.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpUtil {
    public String generateOtp(){
        String number= "0123456789";
        Random random= new Random();
        char[] otpArr= new char[8];
        StringBuilder otpCode= new StringBuilder();
        for (int i=0; i<otpArr.length; i++){
            otpArr[i]= number.charAt(random.nextInt(10));
            otpCode.append(otpArr[i]);
        }
        return otpCode.toString();
    }

}
