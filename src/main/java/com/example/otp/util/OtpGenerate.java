package com.example.otp.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpGenerate {
    public String generateOtp(){
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(1000, 10000);
      return   String.valueOf(otp);
    }
}
