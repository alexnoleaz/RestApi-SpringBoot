package com.example.restapiuserspringbootjpa.services;

import com.example.restapiuserspringbootjpa.entities.User;
import com.example.restapiuserspringbootjpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    // Fields
    private UserRepository userRepository;

    // Constructors
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

        // Inserting data for test endpoints
        this.userRepository.save(new User("Alex", "Nole", "cnole_azabache@outlook.com", 20));
        this.userRepository.save(new User("Alex", "Nole", "cnole_azabache@outlook.com", 20));
        this.userRepository.save(new User("Alex", "Nole", "cnole_azabache@outlook.com", 20));
        this.userRepository.save(new User("Alex", "Nole", "cnole_azabache@outlook.com", 20));
        this.userRepository.save(new User("Alex", "Nole", "cnole_azabache@outlook.com", 20));
        this.userRepository.save(new User("Alex", "Nole", "cnole_azabache@outlook.com", 20));
    }

    // Methods
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public boolean updateUser(User user) {
        boolean exists = userRepository.existsById(user.getId());

        if (exists) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean deleteUser(Long id) {
        boolean exists = userRepository.existsById(id);

        if (exists) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
