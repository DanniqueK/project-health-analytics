package com.project.health_analytics.service;

import java.util.List;

import com.project.health_analytics.model.User;

/**
 * Service interface for User model
 */
public interface UserService {

    // Create

    // Read
    /**
     * Retrieves a list of all users.
     *
     * @return a list of User objects.
     */
    List<User> getAllUsers();

    /**
     * Retrieves a user by BSN.
     *
     * @param bsn the BSN of the user to retrieve.
     * @return the User object.
     */
    User getUserByBsn(Integer bsn);

    // Update

    // Delete
}