package com.example.task_manager_repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@EqualsAndHashCode
public class Tokens {
    @Id
    private String jwtToken;

    @ManyToOne
    @JoinColumn(name = "user_id") // Defines the foreign key column name
    @JsonIgnore
    private User user;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Tokens{" +"username"+user.getFirstName()+'\''+
                "jwtToken='" + jwtToken + '\'' +
                '}';
    }
}
