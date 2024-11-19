package com.example.advancedMapping.controller;

import com.example.advancedMapping.DAO.UserRepository;
import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.models.AuthenticationRequest;
import com.example.advancedMapping.security.JWTSecurity.JwtUtil;
import com.example.advancedMapping.security.UserDetailsServiceImpl;
import com.example.advancedMapping.service.UserServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
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



    @WithMockUser
    @Test
    void login() throws Exception {
        AuthenticationRequest authenticationRequest=new AuthenticationRequest("aya","123");
        String authenticationRequestJson = objectMapper.writeValueAsString(authenticationRequest);

        mockMvc.perform( MockMvcRequestBuilders
                .post("/login")
                .accept(MediaType.APPLICATION_JSON).content(authenticationRequestJson)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @WithMockUser
    @Test
    void returnUser() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/users")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void returnAllUser() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        mockMvc.perform( MockMvcRequestBuilders
                .get("/AllUsers")
                .header("Authorization", token) // Add JWT token here
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void editUser() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        String userJson = objectMapper.writeValueAsString(this.user);
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/users")
                .header("Authorization", token) // Add JWT token here
                .accept(MediaType.APPLICATION_JSON).content(userJson)
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

    @WithMockUser
    @Test
    void addUser() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        String userJson = objectMapper.writeValueAsString(this.user);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .header("Authorization", token) // Add JWT token here
                .accept(MediaType.APPLICATION_JSON).content(userJson)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void logOut()throws Exception{
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        mockMvc.perform( MockMvcRequestBuilders
                .post("/logoutt")
                .header("Authorization", token) // Add JWT token here
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void logOutAll()throws Exception{
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        mockMvc.perform( MockMvcRequestBuilders
                .post("/logoutAll")
                .header("Authorization", token) // Add JWT token here
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}