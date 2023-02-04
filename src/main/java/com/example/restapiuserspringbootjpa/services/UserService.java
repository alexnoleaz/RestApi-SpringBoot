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
        this.userRepository.save(new User("Alex", "Sandoval", "alex-sandoval12@outlook.com", 20));
        this.userRepository.save(new User("Jeyson", "Sanchez", "sanchez@outlook.com", 20));
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

    public User updateUser(User user) {
        Optional<User> res = userRepository.findById(user.getId());
        User userUpdate = new User();

        if (res.isPresent()) {
            userUpdate.setId(user.getId());
            userUpdate.setName(user.getName() != null ? user.getName() : res.get().getName());
            userUpdate.setLastname(user.getLastname() != null ? user.getLastname() : res.get().getLastname());
            userUpdate.setEmail(user.getEmail() != null ? user.getEmail() : res.get().getEmail());
            userUpdate.setAge(user.getAge() != null ? user.getAge() : res.get().getAge());
            return userRepository.save(userUpdate);
        }
        return null;
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
