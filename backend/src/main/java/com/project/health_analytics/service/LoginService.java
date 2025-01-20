package com.project.health_analytics.service;

import com.project.health_analytics.dto.LoginDto;
import com.project.health_analytics.dto.SessionReturnDto;

public interface LoginService {
    /**
     * Authenticates a user based on the provided login credentials.
     *
     * @param loginDto the login credentials.
     * @return true if authentication is successful, false otherwise.
     */
    SessionReturnDto login(LoginDto loginDto);
}
