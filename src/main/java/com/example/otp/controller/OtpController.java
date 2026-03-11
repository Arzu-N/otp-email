package com.example.otp.controller;

import com.example.otp.dto.OtpRequestDto;
import com.example.otp.dto.OtpVerifyingDto;
import com.example.otp.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/otp")
public class OtpController {
    private final OtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<Void>sendOtp(@RequestBody OtpRequestDto dto){
        otpService.sendOtp(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<String>verifyOtp(@RequestBody OtpVerifyingDto dto){
        return ResponseEntity.ok((otpService.verifyingOtp(dto)) ?"otp valid":"otp invalid");
    }
}
