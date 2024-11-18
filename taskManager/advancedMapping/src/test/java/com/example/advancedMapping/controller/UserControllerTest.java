package com.example.advancedMapping.controller;

import com.example.advancedMapping.DAO.UserRepository;
import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.models.AuthenticationResponse;
import com.example.advancedMapping.security.JWTSecurity.JwtUtil;
import com.example.advancedMapping.security.UserDetailsServiceImpl;
import com.example.advancedMapping.service.UserServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(UserController.class)
@SpringBootTest  // For full application context
public class UserControllerTest {
    //A mock object for simulating HTTP requests and responses. It is used to test the behavior of the controller.
    @Autowired
    MockMvc mockMvc;

    // A utility to convert Java objects to JSON and vice versa. It's used to serialize and deserialize data in tests.
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserServiceImplementation userServiceImplementation;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    JwtUtil jwtUtil;

    private final User user=new User(1,"aya","Aya","Jamal","ayabaara@gmail.com","123","user");
    private final User admin=new User(2,"lana","Lana","Jamal","lana@gmail.com","123","admin");
    private final User user2=new User(3,"sama","Sama","sami","sama@gmail.com","123","user");



    @WithMockUser(username = "admin", roles = {"admin"})  // Mock an authenticated user
    @Test
    void createNewUser() throws Exception {
        // Mock the authentication and set the user as "admin"
        Authentication authentication = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Create the user object to send in the request
        String userJson = objectMapper.writeValueAsString(this.user);

        // Perform the request and expect the status to be OK (200)
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void logOut()throws Exception{
        mockMvc.perform( MockMvcRequestBuilders
                .post("/logoutt")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void logOutAll()throws Exception{
        mockMvc.perform( MockMvcRequestBuilders
                .post("/logoutAll")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}