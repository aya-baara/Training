package com.example.advancedMapping.service;

import com.example.advancedMapping.DAO.TaskRepository;
import com.example.advancedMapping.DAO.TokenRepository;
import com.example.advancedMapping.DAO.UserRepository;
import com.example.advancedMapping.entity.Tokens;
import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.exception.NotFoundException;
import com.example.advancedMapping.models.AuthenticationRequest;
import com.example.advancedMapping.models.AuthenticationResponse;
import com.example.advancedMapping.security.JWTSecurity.JwtUtil;
import com.example.advancedMapping.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class UserServiceImplementation implements CRUDService <User>{
    private UserRepository userRepository;
    private final JwtUtil jwtTokenUtil;
    private final TaskRepository taskRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;


    @Autowired
    public UserServiceImplementation(UserDetailsServiceImpl userDetailsService, JwtUtil jwtTokenUtil, TaskRepository taskRepository,
                                     UserRepository theUserRepository, TokenRepository tokenRepository,
                                     AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.taskRepository = taskRepository;
        userRepository = theUserRepository;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> result=userRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        else{throw new NotFoundException("User Not Found");}
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        if(findById(id)==null){
            throw new NotFoundException("User Not Found");
        }
        userRepository.deleteById(id);
    }

    public AuthenticationResponse createAuthenticationToken(AuthenticationRequest authenticationRequest) {
        try {
            System.out.println("i'm here");
            System.out.println("=================>>>"+authenticationRequest);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            System.out.println("i'm here in the exception");
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        Tokens token = new Tokens();
        User user=(User)userDetails;
        token.setUser(user);
        token.setJwtToken(jwt);
        tokenRepository.save(token);
        user.addToken(token);
        userRepository.save(user);
        return new AuthenticationResponse(jwt);
    }
}
