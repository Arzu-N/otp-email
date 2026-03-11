package com.example.otp.dto;

import lombok.Data;

@Data
public class OtpVerifyingDto {
    private String email;
    private String otp;
}
