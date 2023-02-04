package com.example.restapiuserspringbootjpa.controllers;

import com.example.restapiuserspringbootjpa.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests work correctly, when run one by one, the expected results are obtained.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    // Fields
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    // Methods
    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void getUsers() {
        ResponseEntity<User[]> res = testRestTemplate.getForEntity("/api/users", User[].class);
        User userTest = res.getBody()[0];

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(User[].class, res.getBody().getClass());
        assertTrue(res.getBody().length > 0);
        assertTrue("Sandoval".equals(userTest.getLastname()));
    }

    @Test
    void getUser() {
        ResponseEntity<User> resFound = testRestTemplate.getForEntity("/api/users/1", User.class);
        ResponseEntity<User> resNotFound = testRestTemplate.getForEntity("/api/users/10", User.class);

        assertEquals(HttpStatus.OK, resFound.getStatusCode());
        assertEquals("Alex", resFound.getBody().getName());

        assertEquals(HttpStatus.NOT_FOUND, resNotFound.getStatusCode());
    }

    @Test
    void createUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String userJson = """
                {
                    "name": "Cristhian",
                    "lastname" : "Correa",
                    "email" : "cristhian_correa@gmail.com",
                    "age" : 18
                }
                """;
        String userJsonError = """
                {
                    "id" : 10
                    "name": "Sergio",
                    "lastname" : "Garrido",
                    "email" : "garrido@gmail.com",
                    "age" : 18
                }
                """;

        HttpEntity<String> req = new HttpEntity<>(userJson, headers);
        ResponseEntity<User> res = testRestTemplate.exchange("/api/users", HttpMethod.POST, req, User.class);

        assertEquals(HttpStatus.CREATED, res.getStatusCode());
        assertTrue(res.getBody().getId() != null);

        HttpEntity<String> reqError = new HttpEntity<>(userJsonError, headers);
        ResponseEntity<User> resError = testRestTemplate.exchange("/api/users", HttpMethod.POST, reqError, User.class);

        assertEquals(HttpStatus.BAD_REQUEST, resError.getStatusCode());

    }

    @Test
    void updateUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String userDataUpdate = """
                    {
                        "name": "ALEX",
                        "lastname": "SANDOVAL"
                    }
                """;

        HttpEntity<String> req = new HttpEntity<>(userDataUpdate, headers);
        ResponseEntity<User> res = testRestTemplate.exchange("/api/users/1",HttpMethod.PUT,req,User.class);

        assertEquals(HttpStatus.CREATED, res.getStatusCode());
        assertEquals(res.getBody().getName(), "ALEX");
        assertTrue(res.getBody().getAge() == 20);

        ResponseEntity<User> resError = testRestTemplate.exchange("/api/users/10", HttpMethod.PUT, req, User.class);
        assertEquals(HttpStatus.NOT_FOUND, resError.getStatusCode());
    }

    @Test
    void deleteUser() {
        ResponseEntity<User> res = testRestTemplate.exchange("/api/users/1",HttpMethod.DELETE,null, User.class);
        assertTrue(res.getStatusCode().equals(HttpStatus.NO_CONTENT));

        ResponseEntity<User> resError = testRestTemplate.exchange("/api/users/10",HttpMethod.DELETE,null, User.class);
        assertEquals(HttpStatus.NOT_FOUND,resError.getStatusCode());
    }

    @Test
    void deleteUsers() {
        ResponseEntity<User> res = testRestTemplate.exchange("/api/users",HttpMethod.DELETE,null, User.class);
        assertEquals(HttpStatus.NO_CONTENT,res.getStatusCode());
    }
}