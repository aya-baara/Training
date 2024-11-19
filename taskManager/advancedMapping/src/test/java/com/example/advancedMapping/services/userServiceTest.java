package com.example.advancedMapping.services;

import com.example.advancedMapping.DAO.TaskRepository;
import com.example.advancedMapping.DAO.TokenRepository;
import com.example.advancedMapping.DAO.UserRepository;
import com.example.advancedMapping.entity.Tokens;
import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.exception.AlreadyExistException;
import com.example.advancedMapping.models.AuthenticationRequest;
import com.example.advancedMapping.models.AuthenticationResponse;
import com.example.advancedMapping.security.JWTSecurity.JwtUtil;
import com.example.advancedMapping.security.UserDetailsServiceImpl;
import com.example.advancedMapping.service.UserServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class userServiceTest {

    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private JwtUtil jwtTokenUtil;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private UserServiceImplementation userServiceImplementation;

    User user=new User(1,"aya","Aya","Jamal","ayabaara@gmail.com","123");
    private final User admin=new User(2,"lana","Lana","Jamal","lana@gmail.com","123","admin");


    @Test
    void shouldCreateNewUserSuccessfully() throws Exception {

        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.admin);

        when(userRepository.save(user)).thenReturn(user);

        // Act
        User createdUser = userServiceImplementation.addUser(user);

        // Assert
        assertEquals(user, createdUser, "The user should be created successfully");
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() throws Exception {
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.admin);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        // Assert
        assertThrows(AlreadyExistException.class,
                ()-> userServiceImplementation.addUser(this.user));
    }

    @Test
    void getUserInfo() throws Exception{

        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user);

        assertEquals(this.user,userServiceImplementation.getUserInfo());
    }

    @Test
    void updateUser() throws Exception{

        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user);

        when(userRepository.save(this.user)).thenReturn(this.user);

        assertEquals(this.user,userServiceImplementation.updateUser(this.user));

    }


    @Test
    void updateUserFail() throws Exception{

        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user);

        //not same id temp and user 'trying to edit another user'
        User temp=new User(2,"lana","Lana","Jamal","lana@gmail.com","123");


        // Assert
        assertThrows(AccessDeniedException.class,
                ()-> userServiceImplementation.updateUser(temp));

    }

    @Test
    void deleteUser ()throws Exception{
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user);

        userServiceImplementation.deleteUser();

        verify(taskRepository,times(1)).deleteAllByUser_Id(this.user.getId());
        verify(tokenRepository,times(1)).deleteAllByUserId(this.user.getId());
        verify(userRepository,times(1)).deleteById(this.user.getId());


    }

    @Test
    void logOut() throws Exception {
        // Mock the HttpServletRequest (parameter in the original method)
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        // Mock the Authorization header to return a JWT token
        when(request.getHeader("Authorization"))
                .thenReturn("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWFkMTIiLCJleHAiOjE2NDk3OTAxMTYsImlhdCI6MTY0OTc1NDExNn0.qfob_KPRXFRUQMSZdKesqVjqC8o-am__EGeapCc8TV8");

        // Extract the JWT token (removing the "Bearer " prefix)
        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);


        Tokens mockToken = new Tokens();
        mockToken.setJwtToken(jwt);
        when(tokenRepository.findByJwtToken(jwt)).thenReturn(Optional.of(mockToken));

        userServiceImplementation.invalidateToken(jwt);


        verify(tokenRepository, times(1)).delete(mockToken);
    }


    @Test
    void logOutAll() throws Exception {
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user);

        // Mock the HttpServletRequest (parameter in the original method)
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        // Mock the Authorization header to return a JWT token
        when(request.getHeader("Authorization"))
                .thenReturn("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWFkMTIiLCJleHAiOjE2NDk3OTAxMTYsImlhdCI6MTY0OTc1NDExNn0.qfob_KPRXFRUQMSZdKesqVjqC8o-am__EGeapCc8TV8");

        // Extract the JWT token (removing the "Bearer " prefix)
        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);




        userServiceImplementation.invalidateTokenAll(jwt);
        System.out.println("herrr");

        verify(tokenRepository, times(1)).deleteAllByUserId(user.getId());
    }

    @Test
    void createAuthenticationTokenPass() throws Exception{
        AuthenticationRequest authenticationRequest=
                new AuthenticationRequest("aya","123");
        when(userDetailsService.loadUserByUsername
                (authenticationRequest.getUsername())).thenReturn(this.user);
        String token= jwtTokenUtil.generateToken(this.user);
        when(jwtTokenUtil.generateToken(any())).thenReturn(token);
        Tokens tokens = new Tokens();
        tokens.setUser(this.user);
        tokens.setJwtToken(token);
        when(tokenRepository.save(tokens)).thenReturn(tokens);
        this.user.addToken(tokens);
        when(userRepository.save((this.user))).thenReturn(this.user);
        AuthenticationResponse authenticationResponse =new AuthenticationResponse(token);
        assertEquals(authenticationResponse.getJwt(),
                userServiceImplementation.createAuthenticationToken(authenticationRequest).getJwt());

    }
    @Test
    void createAuthenticationTokenFail()throws Exception {
        AuthenticationRequest authenticationRequest=
                new AuthenticationRequest("aya","123");
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (authenticationRequest.getUsername(), authenticationRequest.getPassword())))
                .thenThrow(BadCredentialsException.class);
        assertThrows(BadCredentialsException.class,()->userServiceImplementation.createAuthenticationToken(authenticationRequest));

    }



    @Test
    public void testAddUser(){
        User user =new User("aya", "jamal","ayabaara@gmail");
        user.setUsername("aya");
        userRepository.save(user);
        Optional<User> temp=userRepository.findByUsername("aya");
        assertNotNull(temp);

    }

}
