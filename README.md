OTP Service

This project is an OTP (One-Time Password) service built with Spring Boot. Redis is used for storing OTPs and implementing rate limiting. Email sending functionality is enabled.

Technologies

Java 17+

Spring Boot 3+

Spring Data Redis

Lettuce Redis client

Maven/Gradle

SMTP Email (Gmail example)

Features

Generate OTP and send via email

OTP expiration: 2 minutes

OTP attempt limits: 5 wrong attempts → 10-minute block

Rate limit: maximum 3 OTP requests per 1 minute

Redis is used for storing OTPs and attempts

Setup

Download the .jar file: 1b9acaaa-8c01-49b3-b17e-63812f44d42a.jar

Run Redis server (default: localhost:6379):

docker run -d --name redis -p 6379:6379 redis

Add email parameters in .env or application.properties:

spring.mail.host=smtp.gmail.com spring.mail.port=587 spring.mail.username=your_email@gmail.com spring.mail.password=YOUR_PASSWORD spring.mail.properties.mail.smtp.auth=true spring.mail.properties.mail.smtp.starttls.enable=true

Run the service:

java -jar 1b9acaaa-8c01-49b3-b17e-63812f44d42a.jar

API Endpoints

POST /otp/send – send OTP Body (JSON):

{ "email": "user@example.com" }

POST /otp/verify – verify OTP Body (JSON):

{ "email": "user@example.com", "otp": "1234" }

Redis Keys

OTP:{email} – active OTP

OTP_ATTEMPT:{email} – wrong attempt count

OTP_BLOCK:{email} – block status

OTP_RATE_LIMIT:{email} – counter for rate limiting

Note

5 wrong attempts → 10-minute block

More than 3 OTP requests per 1 minute → Too many requests
