package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    UserRepository userRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    CreateUserRequest createUserRequest;
    ResponseEntity<User> userResponseEntity;
    User savedUser;

    @Before
    public void setUp() {
        userController = new UserController(userRepository, cartRepository, bCryptPasswordEncoder);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("username");
        createUserRequest.setPassword("testPassword");
        createUserRequest.setConfirmPassword("testPassword");


    }

    @Test
    public void findById() throws Exception{
        savedUser = new User();
        savedUser.setId(1);

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(savedUser));
        userResponseEntity = userController.findById(1L);
        assertNotNull(userResponseEntity);

        mockMvc.perform(get("/api/user/id/12"))
                .andExpect(status().isOk());
    }
    @Test
    public void findByUserName() throws Exception{
        savedUser = new User();
        savedUser.setUsername("Muchango");

        when(userRepository.findByUsername(anyString())).thenReturn(savedUser);

        userResponseEntity = userController.findByUserName("Muchango");
        assertNotNull(userResponseEntity);

        mockMvc.perform(get("/api/user/gathee"))
                .andExpect(status().isOk());
    }
    @Test
    public void createUser() {
        when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("hashed");
        userResponseEntity = userController.createUser(createUserRequest);
        savedUser = userResponseEntity.getBody();

        assertNotNull(userResponseEntity);
        assertNotNull(savedUser);

        assertEquals(200, userResponseEntity.getStatusCodeValue());
        assertEquals(0, savedUser.getId());
        assertEquals("username", savedUser.getUsername());
        assertEquals("hashed", savedUser.getPassword());
    }
}