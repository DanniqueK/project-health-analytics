package com.project.health_analytics.util;

import java.sql.Timestamp;
import java.time.Instant;

import com.project.health_analytics.dto.LoginDto;
import com.project.health_analytics.model.Password;

public class TestLogin {

    public static LoginDto createValidLoginDto() {
        LoginDto loginDto = new LoginDto();
        loginDto.setBsnOrEmail("123456789");
        loginDto.setPassword("validPassword123");
        return loginDto;
    }

    public static LoginDto createValidEmailDto() {
        LoginDto loginDto = new LoginDto();
        loginDto.setBsnOrEmail("john@gmail.com");
        loginDto.setPassword("validPassword123");
        return loginDto;
    }

    public static LoginDto createInvalidEmailDto() {
        LoginDto loginDto = new LoginDto();
        loginDto.setBsnOrEmail("notjohn@gmail.com");
        loginDto.setPassword("validPassword123");
        return loginDto;
    }

    public static LoginDto createInvalidPasswordLoginDto() {
        LoginDto loginDto = new LoginDto();
        loginDto.setBsnOrEmail("123456789");
        loginDto.setPassword("invalidPassword");
        return loginDto;
    }

    public static LoginDto createNonexistentUserLoginDto() {
        LoginDto loginDto = new LoginDto();
        loginDto.setBsnOrEmail("987654321");
        loginDto.setPassword("somePassword");
        return loginDto;
    }

    public static Password createValidPassword() {
        String hashedPassword = PasswordUtil.hashPassword("validPassword123");
        return new Password(123456789, hashedPassword, true, Timestamp.from(Instant.now().plusSeconds(3600)));
    }

    public static Password createInvalidPassword() {
        String hashedPassword = PasswordUtil.hashPassword("invalidPassword123");
        return new Password(123456789, hashedPassword, true, Timestamp.from(Instant.now().plusSeconds(3600)));
    }

    public static Password createExpiredPassword() {
        return new Password(123456789, "validPassword123", true, Timestamp.from(Instant.now().minusSeconds(3600)));
    }
}
