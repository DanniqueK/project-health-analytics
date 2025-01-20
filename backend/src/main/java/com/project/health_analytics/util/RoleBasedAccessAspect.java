package com.project.health_analytics.util;

import com.project.health_analytics.annotation.AllowedUserTypes;
import com.project.health_analytics.model.UserType;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Aspect for enforcing role-based access control.
 * 
 * @method checkUserType - Checks if the user type is allowed to access the
 *         method.
 * @method isUserTypeAllowed - Checks if the user type is in the list of allowed
 *         user types.
 */
@Aspect
@Component
public class RoleBasedAccessAspect {

    @Autowired
    private HttpServletRequest request;

    /**
     * Checks if the user type is allowed to access the method.
     *
     * @param allowedUserTypes The annotation containing the allowed user types.
     * @throws ResponseStatusException if the user type is not allowed to access the
     *                                 method.
     */
    @Before("@within(allowedUserTypes) || @annotation(allowedUserTypes)")
    public void checkUserType(AllowedUserTypes allowedUserTypes) {
        UserType userType = (UserType) request.getAttribute("userType");
        if (userType == null || !isUserTypeAllowed(userType, allowedUserTypes.value())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, Constants.ACCESS_DENIED);
        }
    }

    /**
     * Checks if the user type is in the list of allowed user types.
     *
     * @param userType         The user type to check.
     * @param allowedUserTypes The list of allowed user types.
     * @return true if the user type is allowed, false otherwise.
     */
    private boolean isUserTypeAllowed(UserType userType, UserType[] allowedUserTypes) {
        for (UserType allowedUserType : allowedUserTypes) {
            if (allowedUserType == userType) {
                return true;
            }
        }
        return false;
    }
}