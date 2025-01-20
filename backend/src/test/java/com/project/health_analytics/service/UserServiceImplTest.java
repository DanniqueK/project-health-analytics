package com.project.health_analytics.service;

import com.project.health_analytics.model.User;
import com.project.health_analytics.repository.UserRepository;
import com.project.health_analytics.util.TestUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private List<User> users;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        users = TestUser.createUserList();

    }

    @Test
    void testGetAllUsers() {
        // given
        when(userRepository.findAll()).thenReturn(users);

        // when
        List<User> result = userService.getAllUsers();

        // then
        verify(userRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getFirstName() + " " + result.get(0).getLastName());
        assertEquals("Jane Smith", result.get(1).getFirstName() + " " + result.get(1).getLastName());
    }
}