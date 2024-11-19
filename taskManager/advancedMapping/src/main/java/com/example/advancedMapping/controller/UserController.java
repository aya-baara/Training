package com.example.advancedMapping.controller;
import com.example.advancedMapping.DAO.UserRepository;
import com.example.advancedMapping.models.AuthenticationRequest;
import com.example.advancedMapping.models.AuthenticationResponse;
import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;


@RestController
public class UserController {
    private UserServiceImplementation userServiceImplementation;

    @Autowired
    public UserController(UserServiceImplementation userServiceImplementation){
        this.userServiceImplementation=userServiceImplementation;
    }

    @GetMapping("/AllUsers")
    public List<User> getAllUsers() throws AccessDeniedException {


        return userServiceImplementation.findAllUsers();
    }


    @GetMapping("/users")
    public Optional<Optional<User>> getUserById(){
        return Optional.ofNullable(userServiceImplementation.getUser());
    }


//    @PostMapping("/users")
//    public void addUser(@RequestBody User user){
//        user.setId(0);  // in case they pass id in json set it to zero to force add new  not edit
//        userRepository.save(user);
//    }

    @PatchMapping("/users")
    public User updateUser(@RequestBody User user) {
       return userServiceImplementation.updateUser(user);
    }


    @DeleteMapping("/users")
    public void deleteUser(){
        userServiceImplementation.deleteUser();
    }

    //Adding Post Mapping to add new user
    @PostMapping("/register")
    public User createNewUser(@RequestBody User newUser) throws AccessDeniedException {

        return userServiceImplementation.addUser(newUser);
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws BadCredentialsException {
        return  userServiceImplementation.createAuthenticationToken(authenticationRequest);
    }

    @PostMapping("/logoutt")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        // Check if the token starts with "Bearer " and remove that prefix
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " (7 characters)

            System.out.println("Token is " + token);

            boolean isLoggedOut = userServiceImplementation.invalidateToken(token);
            return ResponseEntity.ok("Logged out successfully.");
        }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header is missing or invalid.");

    }


    @PostMapping("/logoutAll")
    public ResponseEntity<String> logoutAll(@RequestHeader("Authorization") String authorizationHeader) {
        System.out.println("here");
        // Check if the token starts with "Bearer " and remove that prefix
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " (7 characters)
            System.out.println("Token is " + token);
             userServiceImplementation.invalidateTokenAll(token);
             return ResponseEntity.ok("Logged out from all devices successfully.");

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header is missing or invalid.");
        }
    }

}
