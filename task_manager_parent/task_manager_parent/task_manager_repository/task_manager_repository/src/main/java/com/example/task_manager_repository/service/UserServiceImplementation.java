package com.example.task_manager_repository.service;


import com.example.task_manager_exception.AlreadyExistException;
import com.example.task_manager_exception.NotFoundException;
import com.example.task_manager_repository.DAO.TaskRepository;
import com.example.task_manager_repository.DAO.TokenRepository;
import com.example.task_manager_repository.DAO.UserRepository;
import com.example.task_manager_repository.entity.Tokens;
import com.example.task_manager_repository.entity.User;
import com.example.task_manager_security.JWTSecurity.JwtUtil;
import com.example.task_manager_security.UserDetailsServiceImpl;
import com.example.task_manager_security.models.AuthenticationRequest;
import com.example.task_manager_security.models.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service

public class UserServiceImplementation {
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

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User getUserInfo(){

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User findById(int id) {
        Optional<User> result=userRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        else{throw new NotFoundException("User Not Found");}
    }
    public Optional<User> getUser(){

        User temp=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(temp.getId());

    }

    public void save(User user) {
        userRepository.save(user);
    }


    @Transactional
    public void delete(int id) {
        if(findById(id)==null){
            throw new NotFoundException("User Not Found");
        }
        userRepository.deleteById(id);
    }


    @Transactional
    public void deleteUser() {
        User requestingUser= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taskRepository.deleteAllByUser_Id(requestingUser.getId());
        tokenRepository.deleteAllByUserId(requestingUser.getId());
        userRepository.deleteById(requestingUser.getId());
    }

    @Transactional
    public User updateUser(User user) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User requestingUser= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user.getId()!= requestingUser.getId()){
            System.out.println("here");
            throw new AccessDeniedException("you can only edit your tasks");


        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        System.out.println("User + " +user);
        System.out.println("requestingUser + " +requestingUser);

        return user;

    }


    @Transactional
    public User addUser(User newUser){
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Role = " +user.getRole());
        if(!user.getRole().equalsIgnoreCase("admin")){
            throw new AccessDeniedException("Only admins can add users");
        }
        if(userRepository.findByUsername(newUser.getUsername()).isPresent()){
            throw new AlreadyExistException();
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        newUser.setId(0);

        userRepository.save(newUser);
        return newUser;
    }

    public AuthenticationResponse createAuthenticationToken(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
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

    public boolean invalidateToken(String token) {
        // Find and delete or invalidate token in the database
        Optional<Tokens> userToken = tokenRepository.findByJwtToken(token);
        if (userToken.isPresent()) {
            tokenRepository.delete(userToken.get());
            return true;
        }
        return false;
    }

    public void invalidateTokenAll(String token) {
        User requestingUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tokenRepository.deleteAllByUserId(requestingUser.getId());
    }

    public List<User>findAllUsers(){
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Role = " +user.getRole());
        if(!user.getRole().equalsIgnoreCase("admin")){
            throw new AccessDeniedException("Only admins can access this");
        }
        return  userRepository.findAll();


    }
}
