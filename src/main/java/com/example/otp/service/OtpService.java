package com.example.otp.service;

import com.example.otp.constants.RedisKeys;
import com.example.otp.dto.OtpRequestDto;
import com.example.otp.dto.OtpVerifyingDto;
import com.example.otp.util.OtpGenerate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final RedisTemplate<String, String> template;
    private final OtpGenerate otpGenerate;
    private final EmailService emailService;

    public void sendOtp(OtpRequestDto dto) {
        Long count = template.opsForValue().increment(RedisKeys.OTP_RATE_LIMIT + dto.getEmail());
        if(count==1)template.expire(RedisKeys.OTP_RATE_LIMIT + dto.getEmail(), Duration.ofMinutes(1));
        if(count>3){
throw new RuntimeException("Too many request");
        }
        checkBlock(dto.getEmail());
        String otp = otpGenerate.generateOtp();
        template.opsForValue().set(RedisKeys.OTP + dto.getEmail(), otp, Duration.ofMinutes(2));
        Long count1 = template.opsForValue().increment(RedisKeys.OTP_ATTEMPT + dto.getEmail());
        if(count1==1) template.expire(RedisKeys.OTP_ATTEMPT+dto.getEmail(),Duration.ofMinutes(2));

        emailService.sendEmail(dto.getEmail(), otp);
    }

    public boolean verifyingOtp(OtpVerifyingDto dto) {
        String redisOtp = template.opsForValue().get(RedisKeys.OTP+dto.getEmail() );
        if (redisOtp == null) {
            throw new RuntimeException("Otp expired ");
        }
        if (redisOtp.equals(dto.getOtp())) {
            template.delete(RedisKeys.OTP+dto.getEmail());
            template.delete(RedisKeys.OTP_ATTEMPT+dto.getEmail());
            return true;
        }
        handleWrongAttempt(dto.getEmail());
        return false;
    }




    private void checkBlock(String email) {
        Boolean blocked = template.hasKey(RedisKeys.OTP_BLOCK + email);
        if (Boolean.TRUE.equals(blocked)) {
            throw new RuntimeException("email blocked");
        }
    }

    private void handleWrongAttempt(String email) {
        Long count = template.opsForValue().increment(RedisKeys.OTP_ATTEMPT+email);
        if (count >= 5) {
            template.opsForValue().set(RedisKeys.OTP_BLOCK + email, "BLOCK", Duration.ofMinutes(10));
            template.delete(RedisKeys.OTP + email);
        }
    }
}
