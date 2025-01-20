package com.project.health_analytics.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.health_analytics.dto.LoginDto;
import com.project.health_analytics.model.Password;
import com.project.health_analytics.model.User;
import com.project.health_analytics.repository.PasswordRepository;
import com.project.health_analytics.repository.UserRepository;
import com.project.health_analytics.util.TestLogin;
import com.project.health_analytics.util.TestUser;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordRepository passwordRepository;

    @Test
    public void testLoginSuccess() throws Exception {
        System.out.println("Should successfully login with valid login data from the controller");

        LoginDto loginDto = TestLogin.createValidLoginDto();
        User user = TestUser.createUser();
        Password password = TestLogin.createValidPassword();

        int bsn = Integer.parseInt(loginDto.getBsnOrEmail());

        // Mock the repository behavior
        when(userRepository.findById(bsn)).thenReturn(Optional.of(user));
        when(passwordRepository.findById(bsn)).thenReturn(Optional.of(password));

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userType").value(user.getUserType().toString()))
                .andExpect(cookie().exists("sessionId"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        System.out.println("Should fail to login with invalid login data from the controller");
        LoginDto loginDto = TestLogin.createInvalidPasswordLoginDto();

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isUnauthorized());
    }

}