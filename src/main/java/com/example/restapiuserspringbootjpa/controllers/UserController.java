package com.example.restapiuserspringbootjpa.controllers;

import com.example.restapiuserspringbootjpa.entities.User;
import com.example.restapiuserspringbootjpa.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class UserController {
    // Fields
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    // Contructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets all the users in the database <p>
     * http://localhost:8080/api/users <p>
     * Response
     *
     * @return
     */
    @GetMapping("/users")
    @ApiOperation("Gets all the users in the database")
    public List<User> getUsers() {
        logger.info("Request to get all users");
        return userService.getUsers();
    }

    /**
     * Get user by id from database <p>
     * http://localhost:8080/api/users/{id} <p>
     * Request <p>
     * Response
     *
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    @ApiOperation("Get user by id from database")
    public ResponseEntity<User> getUser(@ApiParam("Primary key of type long") @PathVariable Long id) {
        Optional<User> optional = userService.getUser(id);

        if (optional.isEmpty()) {
            logger.warn("Trying to get a non existent user");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optional.get());
    }

    /**
     * Create a new user in the database <p>
     * http://localhost:8080/api/users <p>
     * Request <p>
     * Response
     *
     * @param user
     * @return
     */
    @PostMapping("/users")
    @ApiOperation("Create a new user in the database")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getId() != null) {
            logger.warn("Trying to create a new user with id. The id is auto-generated");
            return ResponseEntity.badRequest().build();
        }

        User newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    /**
     * Update user by id in the database <p>
     * http://localhost:8080/api/users/{id} <p>
     * Request <p>
     * Response
     *
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/users/{id}")
    @ApiOperation("Update user by id in the database")
    public ResponseEntity<User> updateUser(@ApiParam("Primary key of type long") @PathVariable Long id, @RequestBody User user) {
        boolean isSuccessful = false;

        user.setId(id);
        isSuccessful = userService.updateUser(user);

        if (!isSuccessful) {
            logger.warn("Trying to update a non existent user");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * Delete user by id in the database <p>
     * http://localhost/api/users/{id} <p>
     * Request <p>
     * Response
     *
     * @param id
     * @return
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@ApiParam("Primary key of type long") @PathVariable Long id) {
        boolean isSuccessful = userService.deleteUser(id);

        if (!isSuccessful) {
            logger.warn("Trying to delete a non existent user");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * Delete all users from database <p>
     * http://localhost/api/users <p>
     * Response
     *
     * @return
     */
    @ApiIgnore
    @DeleteMapping("/users")
    public ResponseEntity<User> deleteUsers() {
        logger.info("Request to delete all users");
        userService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
