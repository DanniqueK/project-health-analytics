package com.project.health_analytics.service;

import com.project.health_analytics.model.User;
import com.project.health_analytics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service class for handling User objects.
 * 
 * @method getAllUsers - Retrieves a list of all users from the repository.
 * @method getUserByBsn - Retrieves a user by BSN from the repository.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    // Create

    // Read
    /**
     * Retrieves a list of all users from the repository.
     *
     * @return a list of User objects.
     */
    @Override
    public List<User> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a user by BSN from the repository.
     *
     * @param bsn the BSN of the user to retrieve.
     * @return the User object.
     */
    @Override
    public User getUserByBsn(Integer bsn) {
        Optional<User> user = userRepository.findById(bsn);
        return user.orElse(null);
    }
    // Update

    // Delete
}