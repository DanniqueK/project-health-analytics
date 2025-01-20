package com.project.health_analytics.util;

import com.project.health_analytics.annotation.AllowedUserTypes;
import com.project.health_analytics.model.UserType;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleBasedAccessAspectTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private RoleBasedAccessAspect roleBasedAccessAspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckUserType_AllowedUser() throws NoSuchMethodException {
        when(request.getAttribute("userType")).thenReturn(UserType.medical_professional);

        Method method = TestController.class.getMethod("medical_professionalEndpoint");
        AllowedUserTypes allowedUserTypes = method.getAnnotation(AllowedUserTypes.class);

        roleBasedAccessAspect.checkUserType(allowedUserTypes);

        // No exception should be thrown
    }

    @Test
    void testCheckUserType_DisallowedUser() throws NoSuchMethodException {
        when(request.getAttribute("userType")).thenReturn(UserType.patient);

        Method method = TestController.class.getMethod("medical_professionalEndpoint");
        AllowedUserTypes allowedUserTypes = method.getAnnotation(AllowedUserTypes.class);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            roleBasedAccessAspect.checkUserType(allowedUserTypes);
        });

        assertEquals("Access denied", exception.getReason());
    }

    @Test
    void testCheckUserType_NoUserType() throws NoSuchMethodException {
        when(request.getAttribute("userType")).thenReturn(null);

        Method method = TestController.class.getMethod("medical_professionalEndpoint");
        AllowedUserTypes allowedUserTypes = method.getAnnotation(AllowedUserTypes.class);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            roleBasedAccessAspect.checkUserType(allowedUserTypes);
        });

        assertEquals("Access denied", exception.getReason());
    }

    // Helper class to simulate a controller with annotated methods
    static class TestController {
        @AllowedUserTypes({ UserType.medical_professional })
        public void medical_professionalEndpoint() {
        }
    }
}