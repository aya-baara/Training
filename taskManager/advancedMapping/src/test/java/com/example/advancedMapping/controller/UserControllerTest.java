package com.example.advancedMapping.controller;

import com.example.advancedMapping.DAO.UserRepository;
import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.models.AuthenticationRequest;
import com.example.advancedMapping.models.AuthenticationResponse;
import com.example.advancedMapping.security.JWTSecurity.JwtUtil;
import com.example.advancedMapping.security.UserDetailsServiceImpl;
import com.example.advancedMapping.service.UserServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(UserController.class)
//@SpringBootTest  // For full application context

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
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

//    @Test
//    @WithMockUser(username = "john", password = "123", roles = "USER")
//    void createAuthenticationToken() throws Exception {
//        // Prepare the authentication request object with username and password
//        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
//        authenticationRequest.setUsername("john");
//        authenticationRequest.setPassword("123");
//
//        // Prepare the AuthenticationResponse mock
//        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//        authenticationResponse.setJwt("mockToken"); // Mock JWT response
//
//        // Mock the behavior of userServiceImplementation to return the mock token
//        Mockito.when(userServiceImplementation.createAuthenticationToken(Mockito.any(AuthenticationRequest.class)))
//                .thenReturn(authenticationResponse);
//
//        // Convert authenticationRequest to JSON
//        String jsonRequest = objectMapper.writeValueAsString(authenticationRequest);
//
//        // Perform the POST request to the login endpoint
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/login")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest) // Pass the JSON request body
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()) // Expect status 200 OK
//                .andExpect((ResultMatcher) jsonPath("$.jwt").value("mockToken")); // Expect the JWT token in the response
//    }

    @WithMockUser
    @Test
    void returnUser() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/users")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }


    @WithMockUser
    @Test
    void deleteUser() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        mockMvc.perform( MockMvcRequestBuilders
                .delete("/users")
                .header("Authorization", token) // Add JWT token here
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}