package com.task.minitweet.domains.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name ="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String biography;
    private String profilePicture;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token>tokens;

}
