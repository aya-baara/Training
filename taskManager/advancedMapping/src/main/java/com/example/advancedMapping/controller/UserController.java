package com.example.advancedMapping.controller;

import com.example.advancedMapping.models.AuthenticationRequest;
import com.example.advancedMapping.models.AuthenticationResponse;

import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api")
public class UserController {
    private UserServiceImplementation userServiceImplementation;

    @Autowired
    public UserController(UserServiceImplementation userServiceImplementation){
        this.userServiceImplementation=userServiceImplementation;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userServiceImplementation.findAll();
    }


    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id){
        return userServiceImplementation.findById(id);
    }


    @PostMapping("/users")
    public void addUser(@RequestBody User user){
        user.setId(0);  // in case they pass id in json set it to zero to force add new  not edit
        userServiceImplementation.save(user);
    }

    @PatchMapping("/users")
    public User updateUser(@RequestBody User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        User temp=userServiceImplementation.findById(user.getId());
        user.setPassword(encoder.encode(temp.getPassword()));
        userServiceImplementation.save(user);
        return user;
    }


    @DeleteMapping("/users")
    public void deleteUser(@RequestParam int id){
        userServiceImplementation.delete(id);
    }

    //Adding Post Mapping to add new user
    @PostMapping("/register")
    public User createNewUser(@RequestBody User newUser)  {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        newUser.setId(0);

        userServiceImplementation.save(newUser);
        return newUser;
    }

    //add mapping fot login authentication
    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws BadCredentialsException {
        return  userServiceImplementation.createAuthenticationToken(authenticationRequest);
    }

}
